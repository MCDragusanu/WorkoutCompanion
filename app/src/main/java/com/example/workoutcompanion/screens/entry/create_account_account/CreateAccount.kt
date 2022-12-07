package com.example.workoutcompanion.screens.entry.create_account_account

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutcompanion.graphs.OnBoardState
import com.example.workoutcompanion.common.UIContent
import com.example.workoutcompanion.common.UIState
import com.example.workoutcompanion.composables.*
import com.example.workoutcompanion.theme.*


object CreateAccount {


    @Composable
    fun Main(
        onSubmitResult: (OnBoardState) -> Unit,
        viewModel: CreateAccountViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
        onNextScreen:()->Unit,
        isCompleted : Boolean = false,
        email: String = "",
        ) {

        when(isCompleted) {
            true -> {
                CompletedScreen(email = viewModel.txtEmail.value) {
                   onNextScreen()
                }
            }

            false-> {
                CreateAccountScreen(viewModel = viewModel, onSubmitResult = onSubmitResult)
            }
        }
    }


    private
    @Composable
    fun CreateAccountScreen(
        viewModel: CreateAccountViewModel,
        onSubmitResult: (OnBoardState) -> Unit
    ) {
        val snackbarHostState = SnackbarHostState()
        val errorFlow by viewModel.error.collectAsState()
        val errorEmail by viewModel.errorEmail.collectAsState()
        val errorPassword by viewModel.errorPassword.collectAsState()
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(background),
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Bottom)
            ) {
                StepProgressBar(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(), stepName = "Step 1", progress = 0.33f
                )
                Headline()
                Spacer(modifier = Modifier.size(50.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MyTextField(modifier = Modifier
                        .weight(1f, true)
                        .wrapContentHeight(),

                        text = viewModel.txtFirstName.value,
                        onValueChanged = { viewModel.setFirstName(it) },
                        label = {
                            Text(text = "First Name", fontSize = 10.sp)
                        },
                        leadingIcon = {},
                        placeholder = {},
                        trailingIcon = {})
                    Spacer(modifier = Modifier.size(4.dp))
                    MyTextField(modifier = Modifier
                        .weight(1f, true)
                        .wrapContentHeight(),
                        text = viewModel.txtLastName.value,
                        onValueChanged = { viewModel.setLastName(it) },
                        label = {
                            Text(text = "Last Name", fontSize = 10.sp)
                        },
                        leadingIcon = {},
                        placeholder = {},
                        trailingIcon = {})
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DateButton(
                        modifier = Modifier
                            .weight(1f, true)
                            .wrapContentHeight(),
                        onGetDate = { viewModel.setDate(it) },
                        state = UIState.Enabled
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    GenderButton(
                        modifier = Modifier
                            .weight(1f, true)
                            .wrapContentHeight(),
                        onGetGender = { viewModel.setGender(it) },
                        startGender = viewModel.gender.value,
                        uiState = UIState.Enabled
                    )
                }
                Column() {
                    EmailField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        onValueChanged = { viewModel.setEmail(it) },
                        text = viewModel.txtEmail.value,
                        state = viewModel.stateEmail.collectAsState().value
                    )
                    AnimatedVisibility(visible = errorEmail != null) {
                        errorEmail?.let {
                            Typography.caption.apply {
                                Text(
                                    text = it.error,
                                    Modifier.padding(horizontal = 16.dp),
                                    color = error
                                )
                            }
                        }
                    }
                }
                Column() {
                    PasswordField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        onValueChanged = { viewModel.setPassword(it) },
                        txt = viewModel.txtPassword.value,
                        state = viewModel.statePassword.collectAsState().value
                    )
                    AnimatedVisibility(visible = errorPassword != null) {
                        errorPassword?.let {
                            Typography.caption.apply {
                                Text(
                                    text = errorPassword!!.error,
                                    Modifier.padding(horizontal = 16.dp),
                                    color = error
                                )
                            }
                        }
                    }
                }
                CTAButton(
                    Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    content = CreateAccountCTAContent() {
                        onSubmitResult(viewModel.getAccountInformation())
                        Log.d("Test", "Result Sent from Create Account Screen")
                    },
                    onClick = { viewModel.createAccount() },
                    startState = UIState.Enabled,
                    stateFlow = viewModel.stateButton
                )
            }
        }
        LaunchedEffect(key1 = errorFlow) {
            if (errorFlow is CreateAccountViewModel.Errors.UnknownError) snackbarHostState.showSnackbar(
                errorFlow!!.error,
                actionLabel = null
            )
        }
    }


    private
    @Composable
    fun CompletedScreen(email:String, onNextScreen: () -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onNextScreen()
                }
                .background(secondary),
            contentAlignment = Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp, CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = onSecondary,
                    modifier = Modifier.size(125.dp)
                )
                Typography.h2.apply {
                    Text(
                        text = "Account created with email $email",
                        Modifier,
                        color = onSecondary,
                        fontSize,
                        fontStyle,
                        fontWeight,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.size(20.dp))
                Typography.h5.apply {
                    Text(
                        text = "Click anywhere to continue your registration",
                        Modifier,
                        color = onSecondary,
                        fontSize,
                        fontStyle,
                        fontWeight,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    @Composable
    private fun CreateAccountCTAContent(
        modifier: Modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(horizontal = 0.dp),
        onCompletedAction: () -> Unit
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
                            text = "Create Account",
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
            backgroundBrush = Brush.horizontalGradient(listOf(primary, secondary)),
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
            backgroundBrush =  Brush.horizontalGradient(listOf(primary, secondary)),
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
            backgroundBrush =  Brush.horizontalGradient(listOf(error, error)),
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
                            text = "Account Created",
                            fontSize = this.fontSize,
                            fontWeight = this.fontWeight,
                            color = contentColor
                        )
                    }
                    AnimatedVisibility(visibleState = playAnimation) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
                onCompletedAction()
            },
            backgroundBrush =  Brush.horizontalGradient(listOf(success, success)),
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
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Typography.h1.apply {
                Text(
                    text = "Account",
                    Modifier,
                    color = textColor,
                    fontSize,
                    fontStyle,
                    fontWeight
                )
            }
            Typography.h3.apply {
                Text(
                    text = "Create an account in order to save all your progress",
                    Modifier,
                    color = textColor.copy(alpha = 0.75f),
                    fontSize,
                    fontStyle,
                    fontWeight,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}