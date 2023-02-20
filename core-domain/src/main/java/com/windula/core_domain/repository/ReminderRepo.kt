package com.windula.core_domain.repository

import com.windula.core_database.entity.ReminderEntity
import com.windula.core_domain.entity.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepo {
    suspend fun addReminder(reminder: Reminder)
//    suspend fun loadRemindersFor(category: Category): Flow<List<Reminder>>

    suspend fun loadReminderById (reminderId:Int): Flow<ReminderEntity>

    suspend fun deleteReminder (reminderId:Reminder)
    suspend fun loadAllReminders(): List<Reminder>
}