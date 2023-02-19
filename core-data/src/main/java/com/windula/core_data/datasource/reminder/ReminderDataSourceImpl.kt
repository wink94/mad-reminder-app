package com.windula.core_data.datasource.reminder

import com.windula.core_database.dao.ReminderDao
import com.windula.core_database.entity.ReminderEntity
import com.windula.core_domain.entity.Reminder
import javax.inject.Inject

class ReminderDataSourceImpl @Inject constructor(
    private val reminderDao: ReminderDao
) : ReminderDataSource {


    override suspend fun addReminder(reminder: Reminder) {
        reminderDao.insertOrUpdate(reminder.toEntity())
    }

//    override suspend fun loadRemindersFor(category: Category): Flow<List<Reminder>> {
//        return reminderDao.findRemindersByCategory(category.categoryId).map { list ->
//            list.map {
//                it.fromEntity()
//            }
//        }
//    }

    override suspend fun loadAllReminders(): List<Reminder> {
        return reminderDao.findAll().map {
            it.fromEntity()
        }
    }

    private fun Reminder.toEntity() = ReminderEntity(
        reminderId = if(this.reminderId != null) this.reminderId else null,
        title = this.title,
        message = this.message,
        locationX = this.locationX,
        locationY = this.locationY,
        reminderTime = this.reminderTime,
        reminderDate = this.reminderDate,
        creationTime = this.creationTime,
        modifiedTime = this.modifiedTime,
        creatorId = this.creatorId,
        reminderSeen = this.reminderSeen
    )

    private fun ReminderEntity.fromEntity() = Reminder(
        reminderId = this.reminderId,
        title = this.title,
        message = this.message,
        locationX = this.locationX,
        locationY = this.locationY,
        reminderTime = this.reminderTime,
        reminderDate = this.reminderDate,
        creationTime = this.creationTime,
        modifiedTime = this.modifiedTime,
        creatorId = this.creatorId,
        reminderSeen = this.reminderSeen
    )
}