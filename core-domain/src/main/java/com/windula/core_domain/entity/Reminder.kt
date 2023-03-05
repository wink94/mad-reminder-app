package com.windula.core_domain.entity

import java.time.LocalDateTime

data class Reminder(
    val reminderId: Int? = null,
    val title: String,
    val message: String,
    val locationX: Double? = 65.06,
    val locationY: Double? = 25.47,
    val image: String? = null,
    val reminderTime: String,
    val reminderDate: String,
    val creationTime: LocalDateTime = LocalDateTime.now(),
    val modifiedTime: LocalDateTime = LocalDateTime.now(),
    val creatorId: String,
    val reminderSeen: Boolean = false,
    val reminderRepeat: String? = null
)