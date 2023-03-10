package com.windula.reminderapp.ui.components

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.windula.reminderapp.R
import com.windula.reminderapp.ui.theme.PlaceholderColor
import com.windula.reminderapp.ui.theme.PrimaryColor
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TimePicker(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    pattern: String = "HH:mm",
    is24HourView: Boolean = true,
) {

    val formatter = DateTimeFormatter.ofPattern(pattern)
    val time = if (value.isNotBlank()) LocalTime.parse(value, formatter) else LocalTime.now()
    val dialog = TimePickerDialog(
        LocalContext.current,
        { _, hour, minute -> onValueChange(LocalTime.of(hour, minute).toString()) },
        time.hour,
        time.minute,
        is24HourView,
    )

    TextField(
        value = value,
        onValueChange = {},
        enabled = false,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(start=5.dp,end=5.dp)
//            .fillMaxWidth(0.5F)
            .clickable { dialog.show() }
            .background(Color.White, RoundedCornerShape(percent = 10),),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        placeholder = {
            Text(text = value, color = PlaceholderColor)
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.clock),
                contentDescription = "",
                tint = PrimaryColor,
                modifier = Modifier.size(24.dp)
            )
        }
    )
}