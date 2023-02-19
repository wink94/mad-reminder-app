package com.windula.core_domain.repository

import com.windula.core_domain.entity.Reminder

interface ReminderRepo {
    suspend fun addReminder(reminder: Reminder)
//    suspend fun loadRemindersFor(category: Category): Flow<List<Reminder>>

    suspend fun loadAllReminders(): List<Reminder>
}