package com.example.workoutcompanion.composables

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutcompanion.common.Date
import com.example.workoutcompanion.common.UIState

import com.example.workoutcompanion.theme.TextFieldTheme
import com.example.workoutcompanion.theme.Typography
import com.example.workoutcompanion.theme.primary
import com.example.workoutcompanion.theme.secondary

object DateButton {

    @Composable
   operator fun invoke(
        modifier: Modifier,
        state: UIState,
        onGetDate: (Date) -> Unit,
        labelText: String = "Birth Day",
        startDate: Date? = null,
        isDarkThemed:Boolean = true,
    ) {
        var currentDate by remember { mutableStateOf(startDate) }
        var showDialog by remember { mutableStateOf(false) }
        Button(
            onClick = { showDialog = !showDialog
                      Log.d("Test" , " is clicked")
                      },
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                Color.Transparent
            ),
            elevation = null,
            contentPadding = PaddingValues(0.dp)
        ) {
            MyTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(),
                text = if (currentDate == null) "" else currentDate!!.asString(),
                onValueChanged = {},
                label = {
                    Typography.h3.apply {
                        Text(
                            text = labelText,
                            Modifier,
                            fontSize = 8.sp,
                            maxLines = 1,
                            fontWeight = fontWeight
                        )
                    }
                },
                isEnabled = false,
                trailingIcon = {},
                placeholder = { },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = null,
                        tint = secondary,
                        modifier = Modifier.size(24.dp)
                    )
                }, readOnly = true,
                textStyle = TextStyle(fontSize = 12.sp),
                isError = state.isError(),
                colors = TextFieldTheme.PrimaryDark()
            )
        }
        AnimatedVisibility(visible = showDialog){
            DialogueDatePicker(
                onDismiss = { showDialog = false },
                onGetDate = {
                 onGetDate(it)
                 currentDate = it
                },
                startDate = if(currentDate==null) Date() else currentDate!!,
                title = "Pick your Birth Day" ,
                caption = ""
            )
        }
    }
}