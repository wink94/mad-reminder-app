package com.windula.reminderapp.dao

import androidx.room.Dao
import androidx.room.Query
import com.windula.reminderapp.model.Reminder

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminder")
    fun getAll(): List<Reminder>
}