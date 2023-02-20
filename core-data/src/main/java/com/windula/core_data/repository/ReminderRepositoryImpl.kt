package com.windula.core_data.repository

import com.windula.core_data.datasource.reminder.ReminderDataSource
import com.windula.core_database.entity.ReminderEntity
import com.windula.core_domain.entity.Reminder
import com.windula.core_domain.repository.ReminderRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val reminderDataSource: ReminderDataSource
):ReminderRepo {
    override suspend fun addReminder(reminder: Reminder) {
        reminderDataSource.addReminder(reminder)
    }

    override suspend fun loadReminderById(reminderId: Int): Flow<ReminderEntity> {
        return reminderDataSource.loadReminderById(reminderId)
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminderDataSource.deleteReminder(reminder)
    }

    override suspend fun loadAllReminders(): List<Reminder> {
        return reminderDataSource.loadAllReminders()
    }
}