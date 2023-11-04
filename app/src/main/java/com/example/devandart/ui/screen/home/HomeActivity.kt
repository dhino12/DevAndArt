package com.example.devandart.ui.screen.home

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.devandart.ui.screen.MainCompose
import com.example.devandart.ui.screen.ViewModelFactory
import com.example.devandart.ui.theme.DevAndArtTheme
import com.example.devandart.utils.MetaGlobalData

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val metaDataGlobal = intent.getParcelableExtra<MetaGlobalData>("METADATA_VALUE") as MetaGlobalData
        Log.e("HomeActivity Cookie", metaDataGlobal.toString())
        if (!metaDataGlobal.token.isNullOrBlank()) {
            ViewModelFactory.getInstance(this, metaDataGlobal.cookie ?: "", metaDataGlobal.token)

            setContent {
                DevAndArtTheme {
                    MainCompose(
                        metaGlobalData = metaDataGlobal
                    )
                }
            }
        }
    }
}