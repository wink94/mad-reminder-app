package com.windula.reminderapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.windula.reminderapp.ui.components.GoogleMapComponent
import com.windula.reminderapp.ui.home.HomeScreen
import com.windula.reminderapp.ui.login.LoginScreen
import com.windula.reminderapp.ui.register.RegisterScreen
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

//        composable(route = "modify_reminder_screen") {
//
//                ModifyReminderScreen(navController)
//
//        }

        composable(route = Screens.GoogleMapComponent.route) {
            GoogleMapComponent(navController)
        }

        composable(route = "reminder_screen/{action}/{reminderId}", arguments = listOf(                                         // declaring argument type
            navArgument("action") { type = NavType.StringType },
            navArgument("reminderId") { type = NavType.StringType },
        )) {
            val reminderId = it.arguments?.getString("reminderId")?.toInt()
            val action = it.arguments?.getString("action")
            if (reminderId==null || reminderId==-1){
                ReminderScreen(navController =navController,reminderId=null,reminderHeader =action)
            }
            else{
                ReminderScreen(reminderId=reminderId, navController = navController, reminderHeader = action)
            }

        }
    }
}