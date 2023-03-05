package com.windula.reminderapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.windula.reminderapp.ui.theme.Poppins
import com.windula.reminderapp.ui.theme.PrimaryColor
import com.windula.reminderapp.ui.theme.Shapes
import com.windula.reminderapp.ui.theme.white

@Composable
fun RepeatReminderDropdown(
    value: String,
    onValueChange: (String) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Select Repeatability (Optional)","Per 5 Minute", "Daily", "Weekly")
    val disabledValue = "B"
    var selectedIndex by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .wrapContentSize(Alignment.TopStart)
    ) {
        Text(
            items[selectedIndex],
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expanded = true })
                .background(
                    PrimaryColor
                ),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = Poppins,
            color = white
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .clip(shape = Shapes.large)
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .background(
                    white
                )
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    onClick = {
                        selectedIndex = index
                        expanded = false
                        onValueChange(if(index==0) "" else items[index])
                    }) {
                    val disabledText = if (s == disabledValue) {
                        " (Disabled)"
                    } else {
                        ""
                    }
                    Text(text = s + disabledText)

                }
            }
        }
    }
}