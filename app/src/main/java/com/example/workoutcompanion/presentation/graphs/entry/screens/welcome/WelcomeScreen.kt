package com.example.workoutcompanion.presentation.graphs.entry.screens.welcome

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.workoutcompanion.ui.theme.TextFieldTheme
import com.example.workoutcompanion.presentation.ui_state.UIContent
import com.example.workoutcompanion.presentation.ui_state.UIState
import com.example.workoutcompanion.ui.theme.Shapes
import com.example.workoutcompanion.ui.theme.Typography

object WelcomeScreen {
    @Composable
    fun Main(
        viewModel: WelcomeScreenViewModel,
        onCreateAccount: () -> Unit
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Background()
            Foreground(viewModel = viewModel, onCreateAccount = onCreateAccount)

        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    private fun Foreground(
        viewModel: WelcomeScreenViewModel,
        onCreateAccount: () -> Unit,
    ) {
        val currentState by viewModel.uiState.collectAsState()
        val showSnackBarError by remember { mutableStateOf(currentState.unknownError != null) }
        val context = LocalContext.current
        if (showSnackBarError) {
            Toast.makeText(context, currentState.unknownError!!.message, Toast.LENGTH_LONG).show()
        }
        val playEntryAnimation = remember {
            MutableTransitionState(false).apply {

                targetState = true
            }
        }
        AnimatedVisibility(
            visibleState = playEntryAnimation,
            enter = fadeIn(tween(750)) + slideInVertically(tween(750)),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                ColorPalette.primaryBackground.copy(
                                    alpha = 0.50f
                                ), Color.Black
                            )
                        )
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Headline()
                Spacer(modifier = Modifier.size(50.dp))

                Column(
                    modifier = Modifier.wrapContentSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start
                ) {
                    EmailField(
                        email = currentState.txtEmail,
                        state = currentState.etEmailState,
                        onValueChanged = {
                            viewModel.onEmailChanged(it)
                        })
                    AnimatedVisibility(visible = currentState.errorEmail != null) {
                        currentState.errorEmail?.let {
                            Typography.caption.apply {
                                Text(
                                    text = it.message,
                                    fontWeight = this.fontWeight,
                                    fontSize = fontSize,
                                    color = ColorPalette.primaryError,
                                    modifier =Modifier.padding(horizontal = 8.dp)
                                )
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier.wrapContentSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start
                ) {
                    PasswordField(
                        password = currentState.txtPassword,
                        state = currentState.etPasswordState,
                        onValueChanged = {

                            viewModel.onPasswordChanged(it)
                        })
                    AnimatedVisibility(visible = currentState.errorPassword != null) {
                        currentState.errorPassword?.let {
                            Typography.caption.apply {
                                Text(
                                    text = it.message,
                                    fontWeight = this.fontWeight,
                                    fontSize = fontSize,
                                    color = ColorPalette.primaryError,
                                    modifier =Modifier.padding(horizontal = 8.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.size(1.dp))

                Column(
                    modifier = Modifier.wrapContentSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { viewModel.onLoginUserWithEmail() },
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 24.dp),
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        elevation = null,
                        contentPadding = PaddingValues(2.dp)
                    ) {
                        AnimatedContent(targetState = currentState.btnLoginState) { uiState ->
                            when (uiState) {
                                UIState.Loading -> {
                                    val content = LoginCTAContent(
                                        Modifier
                                            .fillMaxWidth()
                                            .height(55.dp)
                                    ).find { it.uiState == UIState.Loading }!!
                                    content.drawContent(
                                        content.backgroundBrush,
                                        content.contentColor
                                    )

                                }
                                UIState.Error -> {
                                    val content = LoginCTAContent(
                                        Modifier
                                            .fillMaxWidth()
                                            .height(55.dp)
                                    ).find { it.uiState == UIState.Error }!!
                                    content.drawContent(
                                        content.backgroundBrush,
                                        content.contentColor
                                    )
                                }
                                UIState.Default -> {
                                    val content = LoginCTAContent(
                                        Modifier
                                            .fillMaxWidth()
                                            .height(55.dp)
                                    ).find { it.uiState == UIState.Default }!!
                                    content.drawContent(
                                        content.backgroundBrush,
                                        content.contentColor
                                    )
                                }
                                UIState.Completed -> {
                                    val content = LoginCTAContent(
                                        Modifier
                                            .fillMaxWidth()
                                            .height(55.dp)
                                    ).find { it.uiState == UIState.Completed }!!
                                    content.drawContent(
                                        content.backgroundBrush,
                                        content.contentColor
                                    )
                                }
                                else -> {}
                            }
                        }
                    }
                    AnimatedVisibility(visible = currentState.unknownError != null) {
                        currentState.unknownError?.let {
                            Typography.caption.apply {
                                Text(
                                    text = it.message,
                                    color = ColorPalette.primaryError,
                                    fontSize = this.fontSize,
                                    fontWeight = this.fontWeight,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp)
                                        .wrapContentHeight()
                                )
                            }
                        }
                    }
                }

               Spacer(modifier = Modifier.size(24.dp))

               CreateAccount(onCreateAccount = onCreateAccount)
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
                    fontWeight = FontWeight.Normal,
                    color = this.color.copy(alpha =0.5f), textAlign = TextAlign.End

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
                    color = this.color.copy(alpha =0.5f),
                    fontSize = this.fontSize,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.End
                )
            }
        }
    }

    @Composable
    fun EmailField(
        email: String,
        state: UIState,
        onValueChanged: (String) -> Unit,
        modifier: Modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp)
    ) {
        var currentText by remember { mutableStateOf(email) }
        val currentState by remember { mutableStateOf(state) }
        Log.d("Test", currentState.name)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = currentText,
                textStyle = TextStyle(
                    color = ColorPalette.onPrimarySurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                onValueChange = {
                    currentText = it
                    onValueChanged(currentText)
                },
                label = {
                    Typography.h3.apply {
                        Text(
                            text = "Email",
                            fontSize = this.fontSize,
                            fontWeight = this.fontWeight,
                            color = when (currentState) {
                                UIState.Error -> ColorPalette.primaryError
                                UIState.Completed -> ColorPalette.primarySuccess
                                else -> ColorPalette.onPrimarySurface
                            }
                        )
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
                    when (currentState) {
                        UIState.Error -> {
                            Icon(
                                imageVector = Icons.Filled.Warning,
                                contentDescription = null,
                                tint = ColorPalette.primaryError,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        UIState.Completed -> {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = null,
                                tint = ColorPalette.primarySuccess,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        else -> {}
                    }
                },
                colors = TextFieldTheme.primary(currentState.isError()),
                modifier = modifier,
                shape = Shapes.medium,
                isError = currentState.isError()
            )
        }
    }

    @Composable
    fun PasswordField(
        password: String,
        state: UIState,
        onValueChanged: (String) -> Unit,
        modifier: Modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp)
    ) {
        var currentText by remember { mutableStateOf(password) }
        var showPassword by remember { mutableStateOf(false) }
        val currentState by remember { mutableStateOf(state) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = currentText,
                textStyle = TextStyle(
                    color = ColorPalette.onPrimarySurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                onValueChange = {
                    currentText = it
                    onValueChanged(currentText)
                },
                label = {
                    Typography.h3.apply {
                        Text(
                            text = "Password",
                            fontSize = this.fontSize,
                            fontWeight = this.fontWeight,
                            color = when (currentState) {
                                UIState.Error -> ColorPalette.primaryError
                                UIState.Completed -> ColorPalette.primarySuccess
                                else -> ColorPalette.onPrimarySurface
                            }
                        )
                    }
                },
                colors = TextFieldTheme.primary(currentState.isError()),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                trailingIcon = {

                    when (currentState) {
                        UIState.Error -> {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = null,
                                tint = if (currentState.isError()) ColorPalette.primaryError else ColorPalette.primaryBlue,
                                modifier = Modifier
                                    .size(16.dp)
                                    .clickable {
                                        showPassword = !showPassword
                                    })
                        }
                        UIState.Completed -> {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = null,
                                tint = ColorPalette.primarySuccess,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        else -> {}
                    }

                },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                shape = Shapes.medium,
                modifier = modifier,
                isError = currentState.isError()
            )
        }
    }


    @Composable
    private fun LoginCTAContent(
        modifier: Modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(horizontal = 8.dp)
    ): List<UIContent> {
        val contentList = mutableListOf<UIContent>()
        val defaultContent = UIContent(
            uiState = UIState.Default,
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
            backgroundBrush = ColorPalette.gradientPrimary,
            contentColor = ColorPalette.onPrimaryBlue
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
                            text = "Loading...",
                            fontSize = this.fontSize,
                            fontWeight = this.fontWeight,
                            color = contentColor
                        )
                    }
                    Spacer(modifier = Modifier.size(4.dp))
                    CircularProgressIndicator(
                        modifier = Modifier.size(36.dp),
                        color = contentColor,
                        strokeWidth = 3.dp
                    )
                }
            },
            backgroundBrush = ColorPalette.gradientPrimary,
            contentColor = ColorPalette.onPrimaryBlue
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
            backgroundBrush = ColorPalette.gradientError,
            contentColor = ColorPalette.onPrimaryError
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
                    Spacer(modifier = Modifier.size(4.dp))
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = contentColor,
                        modifier = Modifier.size(36.dp)
                    )
                }
            },
            backgroundBrush = ColorPalette.gradientPrimary,
            contentColor = ColorPalette.onPrimaryBlue
        )
        contentList.add(defaultContent)
        contentList.add(loadingContent)
        contentList.add(errorContent)
        contentList.add(successContent)
        return contentList
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
                    color = ColorPalette.primaryText.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Typography.h4.apply {
                    Text(
                        text = "Create Now",
                        fontSize = fontSize,
                        fontWeight = fontWeight,
                        color = ColorPalette.primaryBlue,
                        modifier = Modifier.clickable {
                            onCreateAccount()
                        }
                    )
                }
            }
        }
    }
}