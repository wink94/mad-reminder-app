package com.windula.reminderapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminder")
data class Reminder(
    @PrimaryKey(autoGenerate = true) val reminderId : Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "location_x") val locationX: String?,
    @ColumnInfo(name = "location_y") val locationY: String?,
    @ColumnInfo(name = "reminder_time") val reminderTime: String,
    @ColumnInfo(name = "creation_time") val creationTime: String,
    @ColumnInfo(name = "modified_time") val modifiedTime: String?,
    @ColumnInfo(name = "creator_id") val creatorId: Int,
    @ColumnInfo(name = "reminder_seen") val reminderSeen: Boolean?,
)
