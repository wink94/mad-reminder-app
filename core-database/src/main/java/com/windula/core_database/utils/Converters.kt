package com.windula.core_database.utils

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class LocalDateTimeConverter {
    @TypeConverter
    fun toLocalDateTime(date: String): LocalDateTime = LocalDateTime.parse(date)

    @TypeConverter
    fun toDateString(date: LocalDateTime): String = date.toString()

    @TypeConverter
    fun toLocalTime(time: String): LocalTime = LocalTime.parse(time)

    @TypeConverter
    fun toLocalTimeString(time: LocalTime): String = time.toString()

    @TypeConverter
    fun toLocalDate(date: String): LocalDate = LocalDate.parse(date)

    @TypeConverter
    fun toLocalTimeString(date: LocalDate): String = date.toString()
}