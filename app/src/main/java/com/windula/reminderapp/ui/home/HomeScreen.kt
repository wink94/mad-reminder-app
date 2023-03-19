package com.windula.reminderapp.ui.home

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.windula.reminderapp.ui.Screens
import com.windula.reminderapp.ui.components.BottomBar
import com.windula.reminderapp.ui.components.TopBar
import com.windula.reminderapp.ui.reminder.ReminderViewModel
import com.windula.reminderapp.ui.reminder.ReminderViewState
import com.windula.reminderapp.util.ComposeFileProvider

//@Preview
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, viewModel: ReminderViewModel = hiltViewModel()) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("reminder_screen/add/-1")
            }) {
                /* FAB content */
                Text(
                    "+",
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                )
            }
        },
        bottomBar = {
            BottomAppBar { BottomBar(navController) }
        }, content = { ReminderList(navController, viewModel) })


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReminderList(navController: NavController, viewModel: ReminderViewModel) {

    viewModel.getAllReminder()

    val reminderViewState by viewModel.reminderState.collectAsState()

    when (reminderViewState) {
        is ReminderViewState.Loading -> {}
        is ReminderViewState.Success -> {
            val reminderList = (reminderViewState as ReminderViewState.Success).data
            LazyColumn() {
                stickyHeader {
                    TopBar(navController, "Reminders") { TopBarAction(navController) }
                }
                items(reminderList) { item ->
                    ReminderCard(
                        item,navController
                    )
                }
            }
        }
    }


}

@Composable
fun TopBarAction(navController: NavController) {

    val context = LocalContext.current

    var hasImage by remember {
        mutableStateOf(false)
    }
    var imageUri by remember {
        mutableStateOf<Uri?>(ComposeFileProvider.getImageUri(context))
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
        }
    )



    IconButton(onClick = {

        cameraLauncher.launch(imageUri)
        println(imageUri)
    }) {
//        Icon(Icons.Filled.Face, null)

        val bitmap = remember { mutableStateOf<Bitmap?>(null) }
        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver, imageUri)
        } else {
            val source =
                ImageDecoder.createSource(context.contentResolver, imageUri!!)
            bitmap.value = ImageDecoder.decodeBitmap(source)
        }

        bitmap.value?.let { btm ->
            Image(
                bitmap = btm.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(5.dp)
            )
        }
    }
    IconButton(onClick = {
        navController.navigate(Screens.Login.route)
    }) {
        Icon(Icons.Filled.ExitToApp, null)
    }
}
