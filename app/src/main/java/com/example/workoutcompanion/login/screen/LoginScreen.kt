package com.example.workoutcompanion.login.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutcompanion.R
import com.example.workoutcompanion.common.FormState
import com.example.workoutcompanion.common.UIState
import com.example.workoutcompanion.common.composables.EmailField
import com.example.workoutcompanion.common.composables.SimplePasswordField
import com.example.workoutcompanion.ui.Typography
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel,
                onSignUp:()->Unit,
                onLogin:(String)->Unit) {
    viewModel.setLoginCallback {
        onLogin(it)
    }
    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState) }) {
       Foreground()
        AnimatedVisibility(visibleState = state , enter = slideInVertically { it }) {
            Background(
                emailFormState = viewModel.emailFormState,
                passwordFormState = viewModel.passwordFormState,
                ctaState = viewModel.ctaState,
                errorChannel = viewModel.errorChannel,
                onEmailChanged = { viewModel.onEmailChanged(it) },
                onPasswordChanged = { viewModel.onPasswordChanged(it) },
                onLogin = { viewModel.onLogin() },
                onForgotPassword = { },
                onSignUp = onSignUp,
                showSnackbar = {
                    scope.launch {
                        snackBarHostState.showSnackbar(context.getString(it))
                    }
                }
            )
        }
    }

}

@Composable
private fun Foreground() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(modifier = Modifier
            .wrapContentSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f), Color.Transparent
                    )
                )
            )){
            Image(
                painter = painterResource(id =/*if(isSystemInDarkTheme()) R.drawable.login_screen_background else*/ R.drawable.workout_tracking_icon),
                contentDescription = null,
                contentScale = ContentScale.None,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun Background(emailFormState: Flow<FormState>,
                       passwordFormState:Flow<FormState>,
                       ctaState:Flow<UIState>,
                       errorChannel:Flow<LoginViewModel.ErrorMessage?>,
                       onEmailChanged:(String)->Unit,
                       onPasswordChanged:(String)->Unit,
                       onForgotPassword:()->Unit,
                       showSnackbar:(Int)->Unit,
                       onLogin:()->Unit,
                       onSignUp: () -> Unit
                       
) {


    val currentError by errorChannel.collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom)
    ) {
        Headline()
        EmailField(
            formState = emailFormState,
            onValueChange = onEmailChanged,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()

        )
        SimplePasswordField(
            formState = passwordFormState,
            onValueChange = onPasswordChanged,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            withErrorMessage = true
        )

        Text(
            text = "Forgot your Account Password?",
            style = Typography.bodySmall,
            modifier = Modifier
                .clickable { onForgotPassword() }
                .padding(vertical = 16.dp))


        LoginButton(
            state = ctaState,
            onClick = onLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp)
        )
        Text(
            text = buildAnnotatedString {
                append("Don't have an Account? ")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 16.sp
                    )
                ) {
                    append("Create Now!")
                }
            }, modifier = Modifier
                .clickable { onSignUp() }, textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.size(16.dp))
    }

    LaunchedEffect(key1 = currentError) {
        Log.d("Test", "Error changed")
        currentError?.let {
            if (it.messageUid in listOf(
                    R.string.login_unknown_error,
                    R.string.error_no_internet_connection
                )
            ) {
                showSnackbar(it.messageUid)
            }
        }
    }
}

@Composable
private fun Headline() {
    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.End
    ) {
        Spacer(modifier = Modifier.size(50.dp))
        Text(text = "Welcome To", style = Typography.headlineSmall, textAlign = TextAlign.End)
        Text(
            text = "Workout Companion",
            style = Typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.fillMaxSize(0.4f))

    }
}

/*@Composable
fun EmailField(formState: Flow<FormState>, onValueChange:(String)->Unit, modifier: Modifier) {
    val currentState by formState.collectAsState(initial = FormState())

    Column(modifier = Modifier.wrapContentSize(), verticalArrangement = Arrangement.spacedBy(8.dp) , horizontalAlignment = Alignment.Start) {
        OutlinedTextField(
            modifier = modifier,
            value = currentState.text,
            onValueChange = onValueChange,
            enabled = !currentState.state.isLoading(),
            isError = currentState.state.isError(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = null) },
            trailingIcon = {
                AnimatedVisibility(visible = currentState.state.isCompleted()) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
                AnimatedVisibility(visible = currentState.state.isError()) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onError
                    )
                }
            },
            label = { Text(text = "Email") })
        AnimatedVisibility(visible = currentState.errorStringResource != null) {
            currentState.errorStringResource?.let {
                Text(text = stringResource(id = it), color = MaterialTheme.colorScheme.error)
            }
        }
    }
}*/

@Composable
private fun LoginButton(state:Flow<UIState> , onClick:()->Unit , modifier: Modifier) {
    val currentState by state.collectAsState(initial = UIState.Enabled)

    val containerColor by animateColorAsState(
        targetValue = when {
            currentState.isDisabled()-> MaterialTheme.colorScheme.surface
            currentState.isCompleted() -> MaterialTheme.colorScheme.secondaryContainer
            currentState.isError() -> MaterialTheme.colorScheme.errorContainer
            else -> MaterialTheme.colorScheme.primary
        }
    )
    val textColor by animateColorAsState(
        targetValue = when {
            currentState.isDisabled()-> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            currentState.isCompleted() -> MaterialTheme.colorScheme.onSecondaryContainer
            currentState.isError() -> MaterialTheme.colorScheme.onErrorContainer
            else -> MaterialTheme.colorScheme.onPrimary
        }
    )
    val iconColor by animateColorAsState(
        targetValue = when {
            currentState.isCompleted() -> MaterialTheme.colorScheme.secondary
            currentState.isError() -> MaterialTheme.colorScheme.onErrorContainer
            else -> MaterialTheme.colorScheme.onPrimary
        }
    )

    // AnimatedContent(targetState = currentState) {
    when {
        currentState.isLoading() -> {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(5.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.secondaryContainer,
                strokeCap = StrokeCap.Round
            )
        }
        else -> {
            Button(
                onClick = onClick,
                modifier = modifier,
                enabled = !currentState.isLoading() && !currentState.isDisabled(),
                colors = ButtonColors(
                    containerColor = containerColor,
                    contentColor = textColor,
                    disabledContainerColor = containerColor,
                    disabledContentColor = textColor
                )
            ) {
                Text(
                    text = when {
                        currentState.isDisabled()->"No Internet Connection"
                        currentState.isError() -> "Retry"
                        currentState.isEnabled() -> "Login Now!"
                        else -> "Login Completed!"
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                Icon(
                    imageVector = when {
                        currentState.isDisabled()->Icons.Filled.SignalWifiConnectedNoInternet4
                        currentState.isError() -> Icons.Filled.Refresh
                        currentState.isEnabled() -> Icons.Filled.Login
                        else -> Icons.Filled.CheckCircle
                    }, contentDescription = null, tint = iconColor
                )
            }
        }
    }
    // }
}