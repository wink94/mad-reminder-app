package com.windula.core_data

import com.windula.core_data.datasource.reminder.ReminderDataSource
import com.windula.core_data.datasource.reminder.ReminderDataSourceImpl
import com.windula.core_data.repository.ReminderRepositoryImpl
import com.windula.core_domain.repository.ReminderRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindReminderDataSource(
        reminderDataSource: ReminderDataSourceImpl
    ): ReminderDataSource

    @Singleton
    @Binds
    fun bindReminderRepository(
        reminderRepository: ReminderRepositoryImpl
    ): ReminderRepo
}