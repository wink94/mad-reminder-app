package com.windula.reminderapp.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.windula.reminderapp.ui.Screens
import com.windula.reminderapp.ui.components.BottomBar
import com.windula.reminderapp.ui.components.TopBar
import com.windula.reminderapp.ui.reminder.ReminderViewModel
import com.windula.reminderapp.ui.reminder.ReminderViewState

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
    IconButton(onClick = {/* Do Something*/ }) {
        Icon(Icons.Filled.Face, null)
    }
    IconButton(onClick = {
        navController.navigate(Screens.Login.route)
    }) {
        Icon(Icons.Filled.ExitToApp, null)
    }
}
