package com.windula.reminderapp.ui.reminder

import com.windula.core_domain.entity.Reminder

sealed interface ReminderViewState {
    object Loading: ReminderViewState
    data class Success(
        val data: List<Reminder>
    ): ReminderViewState
}