package com.example.workoutcompanion.entry_navigation.login.screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutcompanion.R
import com.example.workoutcompanion.common.FormState
import com.example.workoutcompanion.common.UIState
import com.example.workoutcompanion.common.composables.AnimatedStatefulCallToAction
import com.example.workoutcompanion.common.composables.EmailField
import com.example.workoutcompanion.common.composables.SimplePasswordField
import com.example.workoutcompanion.entry_navigation.login.viewmodel.LoginViewModel
import com.example.workoutcompanion.ui.Typography
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel ,
                onSignUp:()->Unit ,
                onLogin:(String)->Unit) {

    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var showPasswordResetEmail by remember { mutableStateOf(false) }
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState) }) {
        Foreground()
        AnimatedVisibility(visibleState = state , enter = slideInVertically { it }) {
            Background(
                emailFormState = viewModel.emailFormState ,
                passwordFormState = viewModel.passwordFormState ,
                ctaState = viewModel.ctaState ,
                errorChannel = viewModel.errorChannel ,
                onEmailChanged = { viewModel.onEmailChanged(it) } ,
                onPasswordChanged = { viewModel.onPasswordChanged(it) } ,
                onLogin = { viewModel.onLogin() } ,
                onForgotPassword = {
                    showPasswordResetEmail = true
                    viewModel.resetDialogueState()
                } ,
                onSignUp = onSignUp ,
                showSnackbar = {
                    scope.launch {
                        snackBarHostState.showSnackbar(context.getString(it))
                    }
                }
            )
        }
    }
    AnimatedVisibility (showPasswordResetEmail) {
        ResetPasswordDialogue(onDismiss = { showPasswordResetEmail = false } ,
            formState = viewModel.resetPasswordFormState ,
            ctaState = viewModel.resetPasswordCtaState ,
            onEmailChanged = {
                   viewModel.onResetPasswordEmailChanged(it)
            } ,
            onSendEmail = {
                viewModel.onSendResetPasswordEmail{
                    showPasswordResetEmail = false
                }
            })
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
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f) , Color.Transparent
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
private fun Background(emailFormState: Flow<FormState> ,
                       passwordFormState:Flow<FormState> ,
                       ctaState:Flow<UIState> ,
                       errorChannel:Flow<LoginViewModel.ErrorMessage?> ,
                       onEmailChanged:(String)->Unit ,
                       onPasswordChanged:(String)->Unit ,
                       onForgotPassword:()->Unit ,
                       showSnackbar:(Int)->Unit ,
                       onLogin:()->Unit ,
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
                .padding(horizontal = 8.dp)
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



@Composable
private fun LoginButton(state:Flow<UIState> , onClick:()->Unit , modifier: Modifier) {
    val currentState by state.collectAsState(initial = UIState.Enabled)


    AnimatedStatefulCallToAction(
        ctaState = state ,
        modifier = modifier ,
        action = onClick
    ) { state , containerColor , contentColor ->
        if (state.isLoading()) {
            LinearProgressIndicator(
                Modifier
                    .fillMaxWidth()
                    .height(2.dp) , color = contentColor
            )
        } else {
            Row(modifier = modifier , verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.Center) {
                Text(
                    text = when {
                        currentState.isDisabled() -> "No Internet Connection"
                        currentState.isError() -> "Retry"
                        currentState.isEnabled() -> "Login Now!"
                        else -> "Login Completed!"
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