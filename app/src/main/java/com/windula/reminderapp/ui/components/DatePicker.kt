package com.windula.reminderapp.ui.components

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.windula.reminderapp.R
import com.windula.reminderapp.ui.theme.PlaceholderColor
import com.windula.reminderapp.ui.theme.PrimaryColor
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DatePicker(
    label: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    pattern: String = "yyyy-MM-dd",
) {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val date = if (value.isNotBlank()) LocalDate.parse(value, formatter) else LocalDate.now()
    val dialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            onValueChange(LocalDate.of(year, month + 1, dayOfMonth).toString())
        },
        date.year,
        date.monthValue - 1,
        date.dayOfMonth,
    )
//    println(date)
    TextField(
        value = value,
        onValueChange = {},
        enabled = false,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(start=5.dp,end=5.dp)
            .fillMaxWidth(0.5F)
            .clickable { dialog.show() }
            .background(Color.White, RoundedCornerShape(percent = 10),),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,

        placeholder = {
            Text(text = value, color = PlaceholderColor)
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = "",
                tint = PrimaryColor,
                modifier = Modifier.size(24.dp)
            )
        }
    )
}