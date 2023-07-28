package com.example.workoutcompanion.on_board.login.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.workoutcompanion.R
import com.example.workoutcompanion.common.composables.FormState
import com.example.workoutcompanion.common.composables.UIState
import com.example.workoutcompanion.common.composables.AnimatedStatefulCallToAction
import com.example.workoutcompanion.common.composables.EmailField
import com.example.workoutcompanion.ui.Typography
import com.example.workoutcompanion.ui.cardShapes
import kotlinx.coroutines.flow.Flow

@Composable
fun ResetPasswordDialogue(onDismiss:()->Unit ,
                          formState: Flow<FormState> ,
                          ctaState:Flow<UIState> ,
                          onEmailChanged:(String)->Unit ,
                          onSendEmail:()->Unit ) {
    val currentState by ctaState.collectAsState(initial = UIState.Enabled)
    Dialog(onDismissRequest = { onDismiss() } ,
        properties = DialogProperties(dismissOnBackPress = true , dismissOnClickOutside = true)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.surface , shape = cardShapes.extraLarge)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding( horizontal = 16.dp , vertical = 24.dp) ,
                verticalArrangement = Arrangement.spacedBy(16.dp , Alignment.Bottom) ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() , horizontalArrangement = Arrangement.End) {
                    Icon(imageVector = Icons.Filled.ExitToApp , contentDescription = null, modifier = Modifier.clickable { onDismiss() })
                }
                Image(
                    painter = painterResource(id = R.drawable.password_reset) ,
                    contentDescription = null ,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight(0.5f),
                    contentScale = ContentScale.Crop,
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() ,
                    horizontalAlignment = Alignment.Start ,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Forgot Your Password?" , style = Typography.headlineMedium)
                    Text(text = "We got you covered , enter the email adress and you will receive a reset password email shortly")
                }
                EmailField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() ,
                    formState = formState ,
                    onValueChange = onEmailChanged
                )
                AnimatedStatefulCallToAction(
                    ctaState = ctaState ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 8.dp) ,
                    action = onSendEmail
                ) { state , containerColor , contentColor ->
                    if (state.isLoading()) {
                        LinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp) , color = contentColor
                        )
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp) ,
                            verticalAlignment = Alignment.CenterVertically ,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = when {
                                    currentState.isDisabled() -> "No Internet Connection"
                                    currentState.isError() -> "Retry"
                                    currentState.isEnabled() -> "Send Email!"
                                    else -> "Email Sent"
                                } , color = contentColor , style = Typography.labelMedium
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            if (!currentState.isCompleted())
                                Icon(
                                    imageVector = when {
                                        currentState.isDisabled() -> Icons.Filled.SignalWifiConnectedNoInternet4
                                        currentState.isError() -> Icons.Filled.Refresh
                                        currentState.isEnabled() -> Icons.Filled.Login
                                        else -> Icons.Filled.CheckCircle
                                    } , contentDescription = null , tint = contentColor
                                )
                        }
                    }
                }
            }
        }
    }
}