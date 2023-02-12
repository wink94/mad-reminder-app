package com.windula.reminderapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TopBar(navController: NavController,headerName:String, content:@Composable() () -> Unit) {
    Column {
        TopAppBar(
            elevation = 4.dp,
            title = {
                Text(
                    headerName,
                    color = Color.Black,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.ExtraBold
                )
            },
            backgroundColor = MaterialTheme.colors.primarySurface,
            actions = {

                content()
            })


    }
}
