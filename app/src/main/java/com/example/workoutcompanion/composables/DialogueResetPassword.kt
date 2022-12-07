package com.example.workoutcompanion.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.workoutcompanion.common.UIContent
import com.example.workoutcompanion.common.UIState
import com.example.workoutcompanion.theme.*
import kotlinx.coroutines.flow.Flow

object DialogueResetPassword {
    @Composable
    operator fun invoke(errorFlow: Flow<String>, stateFlow: Flow<UIState>, onSendEmail:(String)->Unit, onDismiss:()->Unit, onGetEmail:(String)->Unit) {

        var txtEmail by remember { mutableStateOf("") }
        val emailState by stateFlow.collectAsState(initial = UIState.Enabled)
        val errorState by errorFlow.collectAsState(initial = "")


        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
                    .background(background, shape = CardShapes.medium),
                verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.End
            ) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = null,
                    tint = secondary,
                    modifier = Modifier
                        .size(36.dp)
                        .padding(8.dp)
                        .clickable { onDismiss() }
                )
                Spacer(modifier = Modifier.size(16.dp))
                Column(
                    modifier = Modifier.wrapContentSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Typography.h2.apply {
                        Text(
                            text = "Forgot Password?",
                            Modifier,
                            color = textColor,
                            fontSize = fontSize,
                            fontWeight = fontWeight
                        )
                    }
                    Typography.h3.apply {
                        Text(
                            text = "You will receive an email containing the link to reset the password . If you can't find it, please look in the 'Spam' section of your email",
                            modifier = Modifier.padding(8.dp),
                            fontSize = fontSize,
                            color = textColor,
                            fontWeight = fontWeight,
                            textAlign = TextAlign.Center
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        EmailField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(horizontal = 16.dp), onValueChanged = {
                                txtEmail = it
                                onGetEmail(it)
                            }, text = txtEmail, state = emailState,
                        )
                        AnimatedVisibility(visible = emailState.isError()) {
                            if (emailState.isError()) {
                                if (errorState.isNotEmpty()) {
                                    Typography.caption.apply {
                                        Text(
                                            text = errorState,
                                            Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                            color = error,
                                            fontSize,
                                            fontStyle,
                                            fontWeight,
                                            fontFamily,
                                            letterSpacing,
                                            textDecoration,
                                            textAlign = TextAlign.Center,
                                            lineHeight
                                        )
                                    }
                                }
                            }
                        }
                        CTAButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onSendEmail(txtEmail) },
                            content = CTAContent(
                                onCompletedAction = onDismiss
                            ),
                            startState = emailState,
                            stateFlow = stateFlow
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
    private
    @Composable fun CTAContent(modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(55.dp),onCompletedAction:()->Unit):List<UIContent> {
        val contentList = mutableListOf<UIContent>()
            val defaultContent = UIContent(
                uiState = UIState.Enabled,
                content = { brush, contentColor ->
                    Row(
                        modifier = modifier.then(Modifier.background(brush, Shapes.large)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Typography.button.apply {
                            Text(
                                text = "Send Reset Email",
                                fontSize = this.fontSize,
                                fontWeight = this.fontWeight,
                                color = contentColor
                            )
                        }
                        Spacer(modifier = Modifier.size(4.dp))
                        Icon(
                            imageVector = Icons.Filled.Login,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                },
                backgroundBrush = Brush.horizontalGradient(colors = listOf(primary , secondary)),
                contentColor = onPrimary
            )
            val loadingContent = UIContent(
                uiState = UIState.Loading,
                content = { brush, contentColor ->
                    Row(
                        modifier = modifier.then(Modifier.background(brush, Shapes.large)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Typography.button.apply {
                            Text(
                                text = "Loading",
                                fontSize = this.fontSize,
                                fontWeight = this.fontWeight,
                                color = contentColor
                            )
                        }
                        Spacer(modifier = Modifier.size(4.dp))
                        val anim = rememberInfiniteTransition()
                        val angle = anim.animateFloat(
                            initialValue = 0f,
                            targetValue = 360f,
                            animationSpec = InfiniteRepeatableSpec(
                                animation = TweenSpec(),
                                repeatMode = RepeatMode.Restart
                            )
                        )
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier
                                .size(36.dp)
                                .rotate(angle.value)
                        )
                    }
                },
                backgroundBrush =  Brush.horizontalGradient(colors = listOf(primary , secondary)),
                contentColor = onPrimary
            )
            val errorContent = UIContent(
                uiState = UIState.Error,
                content = { brush, contentColor ->
                    Row(
                        modifier = modifier.then(Modifier.background(brush, Shapes.large)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Typography.button.apply {
                            Text(
                                text = "Retry",
                                fontSize = this.fontSize,
                                fontWeight = this.fontWeight,
                                color = contentColor
                            )
                        }
                        Spacer(modifier = Modifier.size(4.dp))
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                },
                backgroundBrush =  Brush.horizontalGradient(colors = listOf(error , error)),
                contentColor = onError
            )
            val successContent = UIContent(
                uiState = UIState.Completed,
                content = { brush, contentColor ->
                    Row(
                        modifier = modifier.then(Modifier.background(brush, Shapes.large)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Typography.button.apply {
                            Text(
                                text = "Email Sent",
                                fontSize = this.fontSize,
                                fontWeight = this.fontWeight,
                                color = contentColor
                            )
                        }
                    }
                    onCompletedAction()
                    },
                    backgroundBrush =  Brush.horizontalGradient(colors = listOf(success , success)),
                contentColor = onSuccess
            )
            contentList.add(defaultContent)
            contentList.add(loadingContent)
            contentList.add(errorContent)
            contentList.add(successContent)
            return contentList
        }

}