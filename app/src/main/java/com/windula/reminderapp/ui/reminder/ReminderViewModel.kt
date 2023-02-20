package com.windula.reminderapp.ui.reminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.windula.core_domain.entity.Reminder
import com.windula.core_domain.repository.ReminderRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(private val reminderRepo: ReminderRepo) : ViewModel() {

    private val _reminderViewState = MutableStateFlow<ReminderViewState>(ReminderViewState.Loading)
    val reminderState: StateFlow<ReminderViewState> = _reminderViewState

    fun saveReminder(reminder: Reminder) {
        println(reminder.toString())
        viewModelScope.launch {
            reminderRepo.addReminder(reminder)
//            notifyUserOfReminder(reminder)
        }
    }

    fun getAllReminder() {
        viewModelScope.launch {
            val reminders = reminderRepo.loadAllReminders()
            _reminderViewState.value =
                ReminderViewState.Success(
                    reminders
                )
        }
    }

    fun getReminderById (reminderId:Int) {
        viewModelScope.launch {
            reminderRepo.loadReminderById(reminderId).collect { response ->

                    _reminderViewState.value =
                        ReminderViewState.SuccessBookById(
                            Reminder(
                                reminderId = response.reminderId,
                                title = response.title,
                                message = response.message,
                                locationX = response.locationX,
                                locationY = response.locationY,
                                reminderTime = response.reminderTime,
                                reminderDate = response.reminderDate,
                                creationTime = response.creationTime,
                                modifiedTime = response.modifiedTime,
                                creatorId = response.creatorId,
                                reminderSeen = response.reminderSeen
                            )
                        )

            }

        }
    }

    fun deleteReminder(reminder:Reminder){
        viewModelScope.launch {
            reminderRepo.deleteReminder(reminder)
        }
    }
}