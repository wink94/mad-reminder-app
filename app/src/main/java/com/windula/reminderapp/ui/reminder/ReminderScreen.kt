package com.windula.reminderapp.ui.reminder

import android.annotation.SuppressLint
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.windula.reminderapp.ui.components.BottomBar
import com.windula.reminderapp.ui.components.TopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReminderScreen(navController: NavController) {

    Scaffold(

        bottomBar = {
            BottomAppBar { BottomBar(navController) }
        },
        topBar = {
            TopBar(navController = navController, headerName = "Add Reminder") {
            }
        },
        content = { ModifyReminderScreen(navController = navController) }
    )

}