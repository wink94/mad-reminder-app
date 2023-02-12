package com.windula.reminderapp.ui.reminder

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.windula.reminderapp.ui.components.DatePicker
import com.windula.reminderapp.ui.components.TimePicker
import com.windula.reminderapp.ui.theme.*

//@Preview
@Composable
fun ModifyReminderScreen (navController: NavController){
    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Image(
            painter = painterResource(id = com.windula.reminderapp.R.drawable.bell_notific_icon),
            contentDescription = "",
            modifier = Modifier.size(80.dp)
        )

        OutlinedTextField(
            value = title, onValueChange = {
                title = it
            },
            label = {
                Text(text = "Title", color = PrimaryColor)
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = PrimaryColor,
                textColor = Color.Black

            ),
            placeholder = {
                Text(text = "Title", color = PlaceholderColor)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType =
                KeyboardType.Text
            ),
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Poppins
            )
        )

        OutlinedTextField(
            value = message, onValueChange = {
                message = it
            },
            label = {
                Text(text = "Message", color = PrimaryColor)
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 20.dp)
                .padding(top = 10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = PrimaryColor,
                textColor = Color.Black
            ),
            placeholder = {
                Text(text = "Message", color = PlaceholderColor)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType =
                KeyboardType.Text
            ),
            singleLine = false,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Poppins
            )
        )


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            backgroundColor = Color.White,
            elevation = 1.dp,
            shape = BottomBoxShape.medium
        ) {
            Row( horizontalArrangement = Arrangement.Center) {
                Column (
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                ) {

                    DatePicker(label = "Date Picker", value = date , onValueChange = {date=it})
                    println(date)
                }
                Column (
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                ) {

                    TimePicker(label = "Time Picker", value = time , onValueChange = {time=it})
                    println(time)
                }
            }

        }



        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Purple500
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp),
            contentPadding = PaddingValues(vertical = 14.dp),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 2.dp
            ),
            shape = Shapes.medium
        ) {
            Text(
                text = "Save",
                fontFamily = Poppins,
                color = white,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }


    }
}