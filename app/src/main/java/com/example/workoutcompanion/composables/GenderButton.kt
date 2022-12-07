package com.example.workoutcompanion.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Man
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutcompanion.common.UIState
import com.example.workoutcompanion.R

import com.example.workoutcompanion.theme.TextFieldTheme
import com.example.workoutcompanion.theme.Typography
import com.example.workoutcompanion.theme.primary
import com.example.workoutcompanion.theme.secondary

object GenderButton {

    @Composable
    operator fun invoke(modifier : Modifier,
                        onGetGender:(Int)->Unit,
                        uiState: UIState,
                        startGender:Int,
                        isDarkThemed:Boolean  =true
             ){
        var currentGender by remember { mutableStateOf(startGender)}
        val choices = stringArrayResource(id = R.array.Genders).toList()
        var show by remember {
            mutableStateOf(false)
        }
        Box(modifier = Modifier.clickable { show = !show }.then(modifier)){
            DropDownMenu(parent = {
                MyTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(),
                    text = if (currentGender == -1) "" else choices[currentGender],
                    onValueChanged = {},
                    label = {
                        Typography.h3.apply {
                            Text(
                                text = "Gender",
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
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Man,
                            contentDescription = null,
                            tint = secondary,
                            modifier = Modifier.size(24.dp)
                        )
                    }, readOnly = true,
                    textStyle = TextStyle(fontSize = 12.sp),
                    isError = uiState.isError(),

                )
            }, getItemSelected = {
                show = false
                currentGender = it
                onGetGender(it)
            }, choices = choices, modifier = modifier, expanded = show)
        }
    }
}