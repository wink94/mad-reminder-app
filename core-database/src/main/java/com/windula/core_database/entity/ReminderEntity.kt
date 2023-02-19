package com.windula.core_database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "reminder",
    indices = [
        Index("reminderId", unique = true),
    ],
    foreignKeys = [
    ]
)
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val reminderId : Int?,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "location_x") val locationX: Double?,
    @ColumnInfo(name = "location_y") val locationY: Double?,
    @ColumnInfo(name = "reminder_time") val reminderTime: String,
    @ColumnInfo(name = "reminder_date") val reminderDate: String,
    @ColumnInfo(name = "creation_time") val creationTime: LocalDateTime,
    @ColumnInfo(name = "modified_time") val modifiedTime: LocalDateTime,
    @ColumnInfo(name = "creator_id") val creatorId: String,
    @ColumnInfo(name = "reminder_seen") val reminderSeen: Boolean,
)
