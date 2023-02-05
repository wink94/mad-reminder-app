package com.windula.reminderapp.dto

import androidx.compose.ui.graphics.Color

data class ReminderCardData(
    var header: String, // Header
    var description: String, // Description
    var color: Color, // Color
    var date: String,
    var backgroundColor: Color,
    var circleColor: Color
)

