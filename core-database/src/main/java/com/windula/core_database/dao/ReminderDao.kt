package com.windula.core_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.windula.core_database.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(payment: ReminderEntity)

    @Query("SELECT * FROM reminder")
    suspend fun findAll(): List<ReminderEntity>

    @Query("SELECT * FROM reminder WHERE reminderId = :reminderId")
    fun findOne(reminderId: Long): Flow<ReminderEntity>


    @Delete
    suspend fun delete(reminder: ReminderEntity)
}