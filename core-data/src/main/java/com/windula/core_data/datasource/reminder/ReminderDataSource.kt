package com.windula.core_data.datasource.reminder

import com.windula.core_domain.entity.Reminder

interface ReminderDataSource {
    suspend fun addReminder(reminder: Reminder)
//    suspend fun loadRemindersFor(category: Category): Flow<List<Reminder>>

    suspend fun loadAllReminders(): List<Reminder>
}