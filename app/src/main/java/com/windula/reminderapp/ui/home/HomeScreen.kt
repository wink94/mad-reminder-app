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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.windula.reminderapp.dto.ReminderCardData
import com.windula.reminderapp.ui.Screens
import com.windula.reminderapp.ui.components.BottomBar
import com.windula.reminderapp.ui.components.TopBar

//@Preview
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {


    val list = listOf(

        ReminderCardData(
            header = "test",
            "test",
            "2023-03-06",
        ),
        ReminderCardData(
            header = "test",
            "test",
            "2023-03-05",
        ),
        ReminderCardData(
            header = "test",
            "test",
            "2023-03-06",
        ),
        ReminderCardData(
            header = "test",
            "test",
            "2023-03-05",
        ),
        ReminderCardData(
            header = "test",
            "test",
            "2023-03-01",
        ),
        ReminderCardData(
            header = "test",
            "test",
            "2023-03-01",
        ),
        ReminderCardData(
            header = "test",
            "test",
            "2023-03-01",
        ),
        ReminderCardData(
            header = "test",
            "test",
            "2023-03-01",
        ),
        ReminderCardData(
            header = "test",
            "test",
            "2023-03-01",
        ),
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /* ... */ }) {
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
        }
    ) {
        ReminderList(dataList = list,navController)
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReminderList(dataList: List<ReminderCardData>,navController:NavController) {


    LazyColumn() {
        stickyHeader {
            TopBar(navController,"Reminders"){TopBarAction(navController)}
        }
        items(dataList) { item ->
            ReminderCard(
                header = item.header,
                description = item.description,
                date = item.date
            )
        }
    }
}
@Composable
fun TopBarAction(navController:NavController) {
    IconButton(onClick = {/* Do Something*/ }) {
        Icon(Icons.Filled.Face, null)
    }
    IconButton(onClick = {
        navController.navigate(Screens.Login.route)
    }) {
        Icon(Icons.Filled.ExitToApp, null)
    }
}
