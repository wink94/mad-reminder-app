package com.windula.reminderapp.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.windula.reminderapp.dto.BottomNavItem

@Composable
fun BottomBar(navController: NavController) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val bottomNavItems = listOf(
        BottomNavItem(
            name = "Home",
            route = "home_screen",
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
                selected = false,
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