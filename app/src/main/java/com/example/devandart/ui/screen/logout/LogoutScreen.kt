package com.example.devandart.ui.screen.logout

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devandart.MainActivity
import com.example.devandart.moveToHomeActivity
import com.example.devandart.ui.common.UiState
import com.example.devandart.ui.component.alert.AlertDialog
import com.example.devandart.ui.screen.ViewModelFactory
import com.example.devandart.ui.screen.home.Fixiv.illustrations.IllustrationsViewModel
import com.example.devandart.ui.screen.home.HomeActivity
import com.example.devandart.ui.screen.login.ItemCookie
import com.example.devandart.ui.screen.login.UserItem
import com.example.devandart.utils.MetaGlobalData
import com.example.devandart.utils.UserData

@Composable
fun LogoutScreen(
    navigateUp: () -> Unit,
    metaGlobalData: MetaGlobalData,
    viewModel: LogoutViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
) {
    val openAlertDialog = remember { mutableStateOf(true) }
    val idUser = remember { mutableStateOf("") }
    Log.e("metaglobaldata", metaGlobalData.toString())

    if (openAlertDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openAlertDialog.value = false
                navigateUp()
            },
            onConfirmation = {
                openAlertDialog.value = false
                viewModel.deleteCookieDb(ItemCookie(
                    id = metaGlobalData.id ?: 1,
                    cookie = "",
                    tokenCsrf = ""
                ))
                viewModel.deleteUserDb(UserItem(
                    id = metaGlobalData.userData?.id ?: "",
                    pixivId = "",
                    name = "",
                    profileImg= "",
                    profileImgBig= "",
                    premium = false,
                    adult= false,

                ))
                idUser.value = metaGlobalData.userData?.id ?: ""
                println("Confirmation registered") // Add logic here to handle confirmation.
            },
            dialogTitle = "Logout",
            dialogText = "Are you sure to logout ?.",
            icon = Icons.Default.ExitToApp
        )
    }

    if (idUser.value.isNotBlank()) {
        val intent = Intent(LocalContext.current, MainActivity::class.java)
        val context = LocalContext.current as Activity
        ViewModelFactory.destroyInstance()
        context.startActivity(intent)
        context.finish()
    }
}