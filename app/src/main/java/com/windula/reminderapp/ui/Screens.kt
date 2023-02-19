package com.windula.reminderapp.ui

sealed class Screens(val route: String) {
    object Login: Screens("login_screen")
    object Home: Screens("home_screen")

    object Register: Screens("register_screen")

    object ModifyReminder: Screens("modify_reminder_screen")

    object GoogleMapComponent: Screens("google_map")

    object AddReminderComponent : Screens("add_reminder")

}