package com.example.workoutcompanion.presentation

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutcompanion.ui.theme.ColorPalette
import com.example.workoutcompanion.R
import com.example.workoutcompanion.ui.theme.Shapes
import com.example.workoutcompanion.ui.theme.Typography

object WelcomeScreen {
    @Composable
    fun Main(
        onStartLogin: (email: String, password: String) -> Unit,
        state: WelcomeScreenState,
        onStateChanged: (WelcomeScreenState) -> Unit
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Background()
            Foreground(state = state, onStateChanged = onStateChanged, onStartLogin = onStartLogin)
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    private fun Foreground(
        state: WelcomeScreenState,
        onStateChanged: (WelcomeScreenState) -> Unit,
        onStartLogin: (email: String, password: String) -> Unit
    ) {
        val currentState by remember { mutableStateOf(state) }
        var btnLoginState by remember {
            mutableStateOf(state.btnLoginState)
        }
        val playEntryAnimation = remember {
            MutableTransitionState(false).apply {
                // Start the animation immediately.
                targetState = true
            }
        }
        AnimatedVisibility(visibleState = playEntryAnimation, enter = fadeIn() + slideInVertically({
            -it
        }), exit = fadeOut()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                ColorPalette.primaryBackground.copy(
                                    alpha = 0.75f
                                ), Color.Black
                            )
                        )
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Headline()
                Spacer(modifier = Modifier.size(50.dp))
                EmailField(
                    email = currentState.txtEmail,
                    error = currentState.error,
                    isOk = currentState.isLoggedIn,
                    onValueChanged = {
                        currentState.txtEmail = it
                        if(!currentState.emailFormatIsValid()) btnLoginState = CallToActionEvent.Error
                        onStateChanged(currentState)
                    })
                PasswordField(
                    password = currentState.txtPassword,
                    error = currentState.error,
                    isOk = currentState.isLoggedIn,
                    onValueChanged = {
                        currentState.txtPassword = it
                        if(!currentState.passwordFormatIsValid()) btnLoginState = CallToActionEvent.Error
                        onStateChanged(currentState)
                    })
                LoginCTA(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 8.dp),
                    onClick = {
                        if (currentState.formIsValid()) {
                            onStartLogin(
                                currentState.txtEmail,
                                currentState.txtPassword
                            )
                        } else {
                            Log.d("Test", "Form is invalid")
                        }
                    },
                    state = btnLoginState
                )
            }
        }
    }
    @Composable
    private fun Background() {
        Image(
            painter = painterResource(id = R.drawable.image_welcome_screen),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize(),

        )
    }

    @Composable
    private fun Headline() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ) {
            Typography.h3.apply {
                Text(
                    text = "Welcome to",
                    fontSize = this.fontSize,
                    fontWeight = this.fontWeight,
                    color = this.color, textAlign = TextAlign.End

                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            Typography.h1.apply {
                Text(
                    text = "Workout \n Companion",
                    fontSize = this.fontSize,
                    fontWeight = this.fontWeight,
                    color = this.color,
                    textAlign = TextAlign.End
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            Typography.h3.apply {
                Text(
                    text = "Your complete workout \n assistant app",
                    color = this.color,
                    fontSize = this.fontSize,
                    fontWeight = this.fontWeight,
                    textAlign = TextAlign.End
                )
            }
        }
    }

    @Composable
    private fun EmailField(
        email: String,
        error: WelcomeScreenState.Error?,
        isOk: Boolean,
        onValueChanged: (String) -> Unit,
        modifier : Modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp)
    ) {
        var currentState by remember { mutableStateOf(email) }
        val errEmail by remember { mutableStateOf(error != null) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = currentState,
                textStyle = TextStyle(
                    color = when {
                        isOk -> ColorPalette.onPrimarySuccess
                        errEmail -> ColorPalette.onPrimaryError
                        else -> ColorPalette.onPrimarySurface
                    },
                    fontSize = 16.sp ,
                    fontWeight = FontWeight.SemiBold
                ),
                onValueChange = {
                    currentState = it
                    onValueChanged(currentState)
                },
                label = {
                    Typography.h3.apply {
                        Text(text = "Email", fontSize = this.fontSize, fontWeight = this.fontWeight)
                    }
                },
                placeholder = {
                    Typography.h3.apply {
                        Text(
                            text = "jon_snow@example.com",
                            fontSize = this.fontSize,
                            fontWeight = this.fontWeight
                        )
                    }
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                trailingIcon = {
                    if (errEmail) {
                        Icon(
                            imageVector = Icons.Filled.Warning,
                            contentDescription = null,
                            tint = ColorPalette.primaryError,
                            modifier = Modifier.size(16.dp)
                        )
                    } else if (isOk) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = ColorPalette.primarySuccess,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                },
                colors = TextFieldTheme.primary(errEmail),
                modifier = modifier,
                shape = Shapes.medium,
                isError = errEmail
            )

            when (error) {
                is WelcomeScreenState.Error.EmailInvalid,
                WelcomeScreenState.Error.EmptyEmail
                -> {
                    Typography.caption.apply {
                        Text(
                            text = error.message,
                            color = ColorPalette.primaryError,
                            fontSize = this.fontSize,
                            fontWeight = this.fontWeight
                        )
                    }
                }
                else -> {}
            }
        }
    }


    @Composable
    private fun PasswordField(
        password: String,
        error: WelcomeScreenState.Error?,
        isOk: Boolean,
        onValueChanged: (String) -> Unit,
        modifier : Modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp)
    ) {
        var currentState by remember { mutableStateOf(password) }
        var showPassword by remember { mutableStateOf(false) }
        val errPassword by remember { mutableStateOf(error != null) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = currentState,
                textStyle = TextStyle(
                    color = when {
                        isOk -> ColorPalette.onPrimarySuccess
                        errPassword -> ColorPalette.onPrimaryError
                        else -> ColorPalette.onPrimarySurface
                    },
                    fontSize = 16.sp ,
                    fontWeight = FontWeight.SemiBold
                ),
                onValueChange = {
                    currentState = it
                    onValueChanged(currentState)
                },
                label = {
                    Typography.h3.apply {
                        Text(
                            text = "Password",
                            fontSize = this.fontSize,
                            fontWeight = this.fontWeight
                        )
                    }
                },
                colors = TextFieldTheme.primary(errPassword),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                trailingIcon = {
                    if (!isOk) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = null,
                            tint = if (errPassword) ColorPalette.primaryError else ColorPalette.primaryBlue,
                            modifier = Modifier
                                .size(16.dp)
                                .clickable {
                                    showPassword = !showPassword
                                })
                    } else {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = ColorPalette.primarySuccess,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                shape = Shapes.medium,
                modifier = modifier,
                isError = errPassword
            )

            when (error) {
                is WelcomeScreenState.Error.PasswordInvalid, WelcomeScreenState.Error.EmptyPassword, WelcomeScreenState.Error.ShortPassword -> {
                    Typography.caption.apply {
                        Text(
                            text = error.message,
                            color = ColorPalette.primaryError,
                            fontSize = this.fontSize,
                            fontWeight = this.fontWeight
                        )
                    }
                }
                else -> {}
            }
        }
    }

    @Composable
    private fun LoginCTA(modifier: Modifier, onClick: () -> Unit, state: CallToActionEvent) {
        val contentColor = when (state) {
            CallToActionEvent.Error -> ColorPalette.onPrimaryError
            CallToActionEvent.Completed -> ColorPalette.onPrimarySuccess
            else -> ColorPalette.onPrimaryBlue
        }
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            elevation = null,
            contentPadding = PaddingValues(0.dp),
            modifier = modifier.wrapContentSize()
        ) {
            when (state) {
                CallToActionEvent.Neutral -> {
                    Row(
                        modifier = Modifier
                            .background(
                                ColorPalette.gradientPrimary,
                                shape = Shapes.medium
                            )
                            .clip(Shapes.medium)
                            .fillMaxSize(),
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
                        Spacer(modifier = Modifier.size(8.dp))
                        Icon(
                            imageVector = Icons.Filled.Login,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                CallToActionEvent.Error -> {
                    Row(
                        modifier = Modifier
                            .background(
                                ColorPalette.gradientError,
                                shape = Shapes.medium
                            )
                            .clip(Shapes.medium)
                            .fillMaxSize(),
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
                        Spacer(modifier = Modifier.size(8.dp))
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                CallToActionEvent.Completed -> {
                    Row(
                        modifier = Modifier
                            .background(
                                ColorPalette.gradientSuccess,
                                shape = Shapes.medium
                            )
                            .clip(Shapes.medium)
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Typography.button.apply {
                            Text(
                                text = "Logged In!",
                                fontSize = this.fontSize,
                                fontWeight = this.fontWeight,
                                color = contentColor
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                CallToActionEvent.Loading -> {
                    Row(
                        modifier = Modifier
                            .background(
                                ColorPalette.gradientPrimary,
                                shape = Shapes.medium
                            )
                            .clip(Shapes.medium)
                            .fillMaxSize(),
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
                        Spacer(modifier = Modifier.size(8.dp))
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = contentColor,
                            strokeWidth = 3.dp
                        )
                    }
                }
                else -> {}
            }
        }
    }
}