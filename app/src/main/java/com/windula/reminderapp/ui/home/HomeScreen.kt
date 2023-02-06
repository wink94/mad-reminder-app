package com.windula.reminderapp.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.navigation.NavController
import com.windula.reminderapp.dto.ReminderCardData
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.windula.reminderapp.dto.BottomNavItem

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
        ReminderList(dataList = list)
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReminderList(dataList: List<ReminderCardData>) {


    LazyColumn() {
        stickyHeader {
            TopBar()
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
fun TopBar() {
    Column {
        TopAppBar(
            elevation = 4.dp,
            title = {
                Text(
                    "Reminders",
                    color = Color.Black,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.ExtraBold
                )
            },
            backgroundColor = MaterialTheme.colors.primarySurface,
            actions = {

                IconButton(onClick = {/* Do Something*/ }) {
                    Icon(Icons.Filled.Face, null)
                }
            })


    }
}

@Composable
fun BottomBar(navController: NavController) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val bottomNavItems = listOf(
        BottomNavItem(
            name = "Home",
            route = "home",
            icon = Icons.Rounded.Home,
        ),
        BottomNavItem(
            name = "Search",
            route = "search",
            icon = Icons.Rounded.Search,
        ),
        BottomNavItem(
            name = "Settings",
            route = "settings",
            icon = Icons.Rounded.Settings,
        ),
    )

    NavigationBar(
        containerColor = MaterialTheme.colors.primary,
    ) {
        bottomNavItems.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route

            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigate(item.route) },

                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "${item.name} Icon",
                    )
                }
            )
        }
    }

}