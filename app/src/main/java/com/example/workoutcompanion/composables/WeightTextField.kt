package com.example.workoutcompanion.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutcompanion.common.UIState
import com.example.workoutcompanion.common.Weight
import com.example.workoutcompanion.theme.TextFieldTheme
import com.example.workoutcompanion.theme.Typography

object WeightTextField {
    @Composable
    operator fun invoke(modifier : Modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .wrapContentHeight(),
                        startWeight: Weight,
                        onValueChange:(String)->Unit,
                        onSelectionChanged:(Int)->Unit,
                        state:UIState,
                        ){
        var currentText by remember { mutableStateOf(startWeight.asKgsString())}
        var currentSelection by remember { mutableStateOf(0)}
        var show by remember { mutableStateOf(false)}
        var isError by remember { mutableStateOf(state.isError()) }
        val choices = listOf("Kilograms" , "Pounds")
        Row(
            modifier =modifier ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MyTextField(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f, true),
                text = currentText,
                label = { Text(text = "Weight", fontSize = 10.sp) },
                onValueChanged = {

                    currentText = it
                   onValueChange(it)

                },
                leadingIcon = {},
                placeholder = {},
                trailingIcon = {},
                isError = isError,
                keyboardType = KeyboardType.Decimal
            )
            Spacer(modifier = Modifier.size(4.dp))
            Box(modifier = Modifier
                .clickable { show = !show }
                .weight(1f, true)
                .wrapContentHeight()) {
                DropDownMenu(
                    parent = {
                        MyTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(),
                            text = choices[currentSelection],
                            onValueChanged = {},
                            label = {
                                Typography.h3.apply {
                                    Text(
                                        text = "Unit ",
                                        Modifier,
                                        maxLines = 1,
                                        fontSize = 8.sp,
                                        fontWeight = fontWeight
                                    )
                                }
                            },
                            isEnabled = false,
                            trailingIcon = {},
                            placeholder = { },
                            leadingIcon = {},
                            readOnly = true,
                            textStyle = TextStyle(fontSize = 12.sp),
                        )
                    },
                    getItemSelected = {
                        show = false
                        currentSelection = it
                        onSelectionChanged(it)
                    },
                    choices = choices,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    expanded = show
                )

            }
        }
    }
}