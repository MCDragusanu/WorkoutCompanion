package com.example.workoutcompanion.screens.entry.welcome_screen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.workoutcompanion.R
import com.example.workoutcompanion.common.UIContent
import com.example.workoutcompanion.common.UIState
import com.example.workoutcompanion.composables.CTAButton
import com.example.workoutcompanion.composables.DialogueResetPassword
import com.example.workoutcompanion.composables.EmailField
import com.example.workoutcompanion.composables.PasswordField
import com.example.workoutcompanion.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object WelcomeScreen {

    @Composable
    fun Main(viewModel: WelcomeScreenViewModel, onLogin: () -> Unit, onSignUp: () -> Unit) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(background)
                .padding(0.dp)
        ) {
            Background()
            Foreground(
                viewModel = viewModel,
                modifier = Modifier.padding(it),
                onLogin = onLogin,
                onSignUp = onSignUp
            )
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    private
    @Composable
    fun Foreground(
        viewModel: WelcomeScreenViewModel,
        modifier: Modifier = Modifier.fillMaxSize(),
        onLogin: () -> Unit,
        onSignUp: () -> Unit
    ) {
        val playAnimation = remember {
            MutableTransitionState(false).apply {
                // Start the animation immediately.
                targetState = true
            }
        }
        var showForgotPasswordDialogue by remember { mutableStateOf(false) }
        val emailState by viewModel.emailState.collectAsState()
        val passwordState by viewModel.passwordState.collectAsState()

        AnimatedVisibility(
            visibleState = playAnimation, enter = fadeIn(tween(750)) + slideInVertically(
                tween(750)
            ),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                background.copy(alpha = 0.25f),
                                Color.Black
                            )
                        )
                    ), verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Headline()
                Column() {
                    EmailField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 16.dp),
                        text = viewModel.txtEmail.value,
                        onValueChanged = { viewModel.onEmailChanged(it) },
                        state = emailState,
                        colors = {
                            TextFieldTheme.PrimaryDark(isError = emailState.isError())
                        }
                    )
                    AnimatedVisibility(visible = emailState.isError()) {
                        if (emailState.isError()) {
                            Typography.caption.apply {
                                Text(
                                    text = viewModel.error.value?.message ?: "Unknown error",
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    color = error,
                                    fontSize = this.fontSize,
                                    fontWeight = fontWeight
                                )
                            }
                        }
                    }
                }
                Column() {
                    PasswordField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 16.dp),
                        onValueChanged = { viewModel.onPasswordChanged(it) },
                        txt = viewModel.txtPassword.value,
                        state = passwordState,
                        colors = {
                            TextFieldTheme.PrimaryDark(isError = passwordState.isError())
                        }
                    )
                    AnimatedVisibility(visible = passwordState.isError()) {
                        if (passwordState.isError()) {
                            Typography.caption.apply {
                                Text(
                                    text = viewModel.error.value?.message ?: "Unknown error ",
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    color = error,
                                    fontSize = this.fontSize,
                                    fontWeight = fontWeight
                                )
                            }
                        }
                    }

                }
                CTAButton(
                    modifier = Modifier.fillMaxWidth(1f),
                    content = LoginCTAContent(onCompletedAction = { onLogin() }),
                    onClick = { viewModel.onLogin() },
                    stateFlow = viewModel.loginFlow,
                    startState = UIState.Enabled
                )
                ForgotPassword {
                    showForgotPasswordDialogue = !showForgotPasswordDialogue
                }
                CreateAccount {
                    onSignUp()
                }
            }
        }
        var txtResetEmail by remember { mutableStateOf("") }
        val scope = rememberCoroutineScope()
        if (showForgotPasswordDialogue) {
            viewModel.onRestartFlow()
            DialogueResetPassword(
                errorFlow = viewModel.resetPasswordErrorMessage,
                stateFlow = viewModel.resetPasswordUIState,
                onSendEmail = { viewModel.sendForgotPasswordEmail(txtResetEmail) },
                onGetEmail = { txtResetEmail = it },
                onDismiss = {
                    scope.launch {
                        delay(200)
                        showForgotPasswordDialogue = !showForgotPasswordDialogue
                    }
                })
        }
    }
    @Composable
    fun CreateAccount(onCreateAccount: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Typography.h4.apply {
                Text(
                    text = "Don't have an account?",
                    fontSize = fontSize,
                    fontWeight = fontWeight,
                    color = primary.copy(alpha = 0.75f)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Typography.h4.apply {
                    Text(
                        text = "Create Now",
                        fontSize = fontSize,
                        fontWeight = fontWeight,
                        color = primary,
                        modifier = Modifier.clickable {
                            onCreateAccount()
                        }
                    )
                }
            }
        }
    }

    @Composable
    private fun LoginCTAContent(
        modifier: Modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(horizontal = 0.dp),
        onCompletedAction:()->Unit
    ): List<UIContent> {
        val contentList = mutableListOf<UIContent>()
        val playAnimation = remember {
            MutableTransitionState(false).apply {
                // Start the animation immediately.
                targetState = true
            }
        }
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
                            text = "Log in",
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
            backgroundBrush =Brush.linearGradient(listOf(primary, secondary)),
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
            backgroundBrush = Brush.linearGradient(listOf(primary, secondary)),
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
            backgroundBrush = Brush.linearGradient(listOf(error, error)),
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
                            text = "Login Completed",
                            fontSize = this.fontSize,
                            fontWeight = this.fontWeight,
                            color = contentColor
                        )
                    }
                    /*AnimatedVisibility(visibleState = playAnimation) {
                        Icon(imageVector = Icons.Filled.Check, contentDescription = null ,tint = contentColor , modifier = Modifier.size(36.dp) )
                    }*/
                }
                onCompletedAction()
            },
            backgroundBrush = Brush.linearGradient(colors = listOf(success, success)),
            contentColor = onSuccess
        )
        contentList.add(defaultContent)
        contentList.add(loadingContent)
        contentList.add(errorContent)
        contentList.add(successContent)
        return contentList
    }

    private @Composable
    fun Headline() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
        ) {
            Typography.h3.apply {
                Text(
                    text = "Welcome to",
                    Modifier,
                    color = textColor.copy(alpha = 0.75f),
                    fontSize,
                    fontStyle,
                    fontWeight,
                    textAlign = TextAlign.End
                )
            }
            Typography.h1.apply {
                Text(
                    text = "Workout Companion",
                    Modifier,
                    color = textColor,
                    fontSize,
                    fontStyle,
                    fontWeight,
                    textAlign = TextAlign.End
                )
            }
            Typography.h3.apply {
                Text(
                    text = "Your complete workout\n assistant app ",
                    Modifier,
                    color = textColor.copy(alpha = 0.75f),
                    fontSize,
                    fontStyle,
                    fontWeight,
                    textAlign = TextAlign.End
                )
            }
        }
    }

    private @Composable
    fun Background() {
        Image(
            painter = painterResource(id = R.drawable.image_welcome_screen),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize()
        )
    }

    private @Composable
    fun ForgotPassword(
        onForgotPassword: () -> Unit
    ) {
        Typography.h3.apply {
            Text(
                text = "Forgot password",
                modifier = Modifier.clickable {
                    onForgotPassword()
                },
                color = primary.copy(alpha = 0.75f),
                fontSize = fontSize,
                fontWeight = fontWeight
            )
        }
    }
}