package com.example.workoutcompanion.composables

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp

import com.example.workoutcompanion.theme.Shapes
import com.example.workoutcompanion.theme.TextFieldTheme
import com.example.workoutcompanion.theme.onSurface


object MyTextField {

    @Composable
    operator fun invoke(modifier : Modifier,
                        text:String,
                        onValueChanged:(String)->Unit,
                        label: @Composable (() -> Unit),
                        placeholder: @Composable (() -> Unit),
                        leadingIcon: @Composable (() -> Unit),
                        trailingIcon: @Composable (() -> Unit),
                        maxLines:Int = 1,
                        isError:Boolean = false,
                        isEnabled:Boolean = true,
                        readOnly:Boolean = false,
                        shape: Shape = Shapes.medium,
                        keyboardType : KeyboardType = KeyboardType.Text,
                        colors: TextFieldColors = TextFieldTheme.PrimaryDark(),
                        visualTransformation: VisualTransformation = VisualTransformation.None,
                        textStyle: TextStyle =TextStyle(fontSize = 16.sp , fontWeight = FontWeight.Normal , color = onSurface)

    ) {
        androidx.compose.material.OutlinedTextField(
            value = text,
            onValueChange = { onValueChanged(it) },
            modifier = modifier,
            label = label,
            placeholder = placeholder,
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            colors = colors,
            isError = isError,
            enabled = isEnabled,
            readOnly = readOnly,
            shape = shape,
            textStyle = textStyle,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            maxLines = maxLines,
            visualTransformation = visualTransformation
        )

    }
}