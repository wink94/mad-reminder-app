package com.windula.reminderapp.ui.reminder

import android.annotation.SuppressLint
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.windula.reminderapp.ui.components.BottomBar
import com.windula.reminderapp.ui.components.TopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReminderScreen(
    navController: NavController,
    reminderHeader: String?,
    reminderId: Int?,
    viewModel: ReminderViewModel = hiltViewModel()
) {

    Scaffold(

        bottomBar = {
            BottomAppBar { BottomBar(navController) }
        },
        topBar = {
            if (reminderHeader == "add") {
                TopBar(navController = navController, headerName = "Add Reminder") {
                }
            } else if (reminderHeader == "modify") {
                TopBar(navController = navController, headerName = "Modify Reminder") {
                }
            }
        },
        content = {
            if (reminderId != null) {
                println(reminderId)
                viewModel.getReminderById(reminderId)
                val reminderViewState by viewModel.reminderState.collectAsState()

                when (reminderViewState) {
                    is ReminderViewState.Loading -> {}
                    is ReminderViewState.SuccessBookById -> {
                        println((reminderViewState as ReminderViewState.SuccessBookById).data)
                        val reminder = (reminderViewState as ReminderViewState.SuccessBookById).data

                        ModifyReminderScreen(navController = navController,reminder)
                    }
                    else -> {
                        ModifyReminderScreen(navController = navController,null)
                    }
                }
            } else {
                println("new reminder")
                ModifyReminderScreen(navController = navController,null)

            }
             }
    )

}