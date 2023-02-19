package com.windula.reminderapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.windula.reminderapp.ui.components.GoogleMapComponent
import com.windula.reminderapp.ui.home.HomeScreen
import com.windula.reminderapp.ui.login.LoginScreen
import com.windula.reminderapp.ui.register.RegisterScreen
import com.windula.reminderapp.ui.reminder.ModifyReminderScreen
import com.windula.reminderapp.ui.reminder.ReminderScreen

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

        composable(route = Screens.Register.route) {
            RegisterScreen(navController)
        }

        composable(route = Screens.ModifyReminder.route) {
            ModifyReminderScreen(navController)
        }

        composable(route = Screens.GoogleMapComponent.route) {
            GoogleMapComponent(navController)
        }

        composable(route = Screens.AddReminderComponent.route) {
            ReminderScreen(navController)
        }
    }
}