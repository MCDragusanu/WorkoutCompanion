package com.example.workoutcompanion.on_board.presentation.account

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.*
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
import com.example.workoutcompanion.common.composables.AnimatedButton
import com.example.workoutcompanion.ui.Typography

@Composable
fun RegistrationCompletedScreen(onCreateWorkout:()->Unit , onSkip:()->Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) ,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize() ,
            verticalArrangement = Arrangement.spacedBy(8.dp , Alignment.Bottom) ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize(0.6f).padding(8.dp) ,
                verticalArrangement = Arrangement.spacedBy(8.dp , Alignment.CenterVertically) ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = com.example.workoutcompanion.R.drawable.green_check) ,
                    contentDescription = null ,
                    Modifier.size(250.dp)
                )

                Text(
                    text = "Everything is prepared for you!" ,
                    style = Typography.headlineMedium ,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "But before you start , you have a decision to make" ,
                    textAlign = TextAlign.Center
                )

            }

            Column() {
                val source = MutableInteractionSource()
                val isPressed = source.collectIsPressedAsState()
                val scale by animateFloatAsState(
                    targetValue = if (isPressed.value) 1.0f else 0.95f ,
                    finishedListener = {
                        onCreateWorkout()
                    })
                Button(
                    onClick = { onSkip() } , modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp) ,
                    colors = ButtonDefaults.outlinedButtonColors()
                ) {
                    Text(text = "Skip this Step")
                    Icon(imageVector = Icons.Filled.SkipNext , contentDescription = null)
                }

                AnimatedButton(
                    modifier = Modifier ,
                    action = onCreateWorkout
                ) { containerColor , contentColor ->
                    Row(
                        modifier = Modifier.fillMaxWidth().height(55.dp) ,
                        horizontalArrangement = Arrangement.spacedBy(
                            8.dp ,
                            Alignment.CenterHorizontally
                        ) ,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Design my first Training program" ,
                            style = Typography.labelMedium ,
                            color = contentColor
                        )
                    }
                }
            }
        }
    }
}