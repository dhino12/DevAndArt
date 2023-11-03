package com.example.devandart.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

fun LazyListScope.gridContentItem(
    modifier: Modifier = Modifier,
    count: Int,
    nColumns: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    beforeContent: @Composable (modifier:Modifier) -> Unit = {},
    itemContent: @Composable BoxScope.(Int) -> Unit = {},
    afterContent: @Composable (modifier: Modifier) -> Unit = {}
) {
    if (count == 0) {
        item {
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "No Item ...",
                    textAlign = TextAlign.Center
                )
            }
        }
        return
    }
    item {
        beforeContent(modifier)
    }
    gridItems(
        modifier = modifier,
        count= count,
        nColumns= nColumns,
        horizontalArrangement = horizontalArrangement,
        itemContent = itemContent,
    )
    item {
        afterContent(modifier)
    }
}

fun LazyListScope.gridItems(
    modifier: Modifier = Modifier,
    count: Int,
    nColumns: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    itemContent: @Composable BoxScope.(Int) -> Unit,
) {
    gridItems(
        modifier = modifier,
        data = List(count) { it },
        nColumns = nColumns,
        horizontalArrangement = horizontalArrangement,
        itemContent = itemContent,
    )
}

fun <T> LazyListScope.gridItems(
    modifier: Modifier = Modifier,
    data: List<T>,
    nColumns: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    val rows = if (data.isEmpty()) 0 else 1 + (data.count() - 1) / nColumns
    items(rows) { rowIndex ->
        Row(
            horizontalArrangement = horizontalArrangement,
            modifier = modifier
        ) {
            for (columnIndex in 0 until nColumns) {
                val itemIndex = rowIndex * nColumns + columnIndex
                if (itemIndex < data.count()) {
                    val item = data[itemIndex]
                    androidx.compose.runtime.key(key?.invoke(item)) {
                        Box(
                            modifier = Modifier.weight(1f, fill = true),
                            propagateMinConstraints = true
                        ) {
                            itemContent.invoke(this, item)
                        }
                    }
                } else {
                    Spacer(Modifier.weight(1f, fill = true))
                }
            }
        }
    }
}
