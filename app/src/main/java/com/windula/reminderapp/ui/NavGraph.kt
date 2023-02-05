package com.windula.reminderapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.windula.reminderapp.ui.home.HomeScreen
import com.windula.reminderapp.ui.login.LoginScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    )
    {
        composable(route = Screens.Login.route) {
            LoginScreen(navController)
        }

        composable(route = Screens.Home.route) {
            HomeScreen(navController)
        }
    }
}