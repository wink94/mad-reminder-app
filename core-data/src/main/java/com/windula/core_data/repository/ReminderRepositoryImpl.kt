package com.windula.core_data.repository

import com.windula.core_data.datasource.reminder.ReminderDataSource
import com.windula.core_domain.entity.Reminder
import com.windula.core_domain.repository.ReminderRepo
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val reminderDataSource: ReminderDataSource
):ReminderRepo {
    override suspend fun addReminder(reminder: Reminder) {
        reminderDataSource.addReminder(reminder)
    }

    override suspend fun loadAllReminders(): List<Reminder> {
        return reminderDataSource.loadAllReminders()
    }
}