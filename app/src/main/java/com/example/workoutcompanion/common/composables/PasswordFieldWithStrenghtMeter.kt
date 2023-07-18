package com.example.workoutcompanion.common.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.WorkoutCompanionColors
import com.example.compose.WorkoutCompanionTheme
import com.example.workoutcompanion.common.FormState
import com.example.workoutcompanion.common.use_cases.password.PasswordProperties
import com.example.workoutcompanion.on_board.composables.FormField
import kotlinx.coroutines.flow.Flow

@Composable
fun PasswordFieldWithStrengthMeter(formState: Flow<FormState>,
                                   onValueChange:(String)->Unit,
                                   modifier: Modifier,
                                   currentProperties: Flow<List<PasswordProperties>>,
                                   requiredProperties:List<PasswordProperties> = listOf(
                                       PasswordProperties.ContainsUppercase,
                                       PasswordProperties.ContainsDigits,
                                       PasswordProperties.IsLongEnough)){


        Column(
            Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start
        ) {
            SimplePasswordField(
                modifier = modifier,
                formState = formState,
                onValueChange = onValueChange,
                withErrorMessage = false
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                val currentState by formState.collectAsState(initial = FormState())
                val progress =
                    currentProperties.collectAsState(initial = listOf()).value.sumOf { it.weight }
                        .toFloat()
                val progressColor by animateColorAsState(
                    targetValue = when (progress) {
                        in 0.0f..0.33f -> MaterialTheme.colorScheme.onError
                        in 0.33f..0.66f -> Color.Yellow
                        else -> Color.Green
                    }
                )
                LinearProgressIndicator(
                    progress = progress, modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp), color = progressColor, trackColor = Color.Transparent
                )
                requiredProperties.onEach {
                    val color = when{
                        currentState.state.isError() && !it.condition(currentState.text)-> MaterialTheme.colorScheme.onErrorContainer
                        it.condition(currentState.text)-> WorkoutCompanionColors(materialTheme =MaterialTheme.colorScheme).successColor
                        else ->MaterialTheme.colorScheme.onBackground.copy(0.5f)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (it.condition(currentState.text)) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                tint =color,
                                modifier = Modifier.size(10.dp)
                            )
                        } else {
                            Surface(shape = CircleShape , modifier = Modifier.size(8.dp)) {}
                        }
                        Text(
                            text = stringResource(id = it.stringResourceId),
                            color = color,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
