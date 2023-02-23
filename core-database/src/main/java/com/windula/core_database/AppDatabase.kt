package com.windula.core_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.windula.core_database.utils.LocalDateTimeConverter
import com.windula.core_database.dao.ReminderDao
import com.windula.core_database.entity.ReminderEntity

@Database(
    entities = [ReminderEntity::class],
    version = 2
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
}