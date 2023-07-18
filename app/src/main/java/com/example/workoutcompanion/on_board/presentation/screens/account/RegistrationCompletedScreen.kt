package com.example.workoutcompanion.on_board.presentation.screens.account

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutcompanion.ui.Typography

@Composable
fun RegistrationCompletedScreen(onContinue:()->Unit , email:String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxSize(0.6f),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = com.example.workoutcompanion.R.drawable.green_check),
                    contentDescription = null,
                    Modifier.size(250.dp)
                )
                Text(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    ) {
                        append("Account created successfully with email ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        append(email)
                    }
                }, textAlign = TextAlign.Center)
            }

            val source = MutableInteractionSource()
            val isPressed = source.collectIsPressedAsState()
            val scale by animateFloatAsState(
                targetValue = if (isPressed.value) 1.0f else 0.95f,
                finishedListener = {
                    onContinue()
                })
            Button(
                onClick = { onContinue()}, modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(8.dp)
                    .scale(scale),
                interactionSource = source
            ) {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = null)
                Text(text = "Create Profile")
            }
        }
    }
}