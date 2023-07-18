package com.example.workoutcompanion.on_board.presentation.screens.account

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.compose.WorkoutCompanionColors
import com.example.workoutcompanion.R
import com.example.workoutcompanion.common.FormState
import com.example.workoutcompanion.common.UIState
import com.example.workoutcompanion.common.composables.EmailField
import com.example.workoutcompanion.common.composables.PasswordFieldWithStrengthMeter
import com.example.workoutcompanion.common.use_cases.password.PasswordProperties
import com.example.workoutcompanion.on_board.composables.TermsOfUseDialogue
import com.example.workoutcompanion.ui.Typography
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

@Composable
fun AccountScreen(emailFormState:Flow<FormState>,
                  passwordFormState:Flow<FormState>,
                  termsIsChecked:Flow<Boolean>,
                  termState:Flow<UIState>,
                  ctaState:Flow<UIState>,
                  errorChannel:Flow<Int?>,
                  passwordProperties: Flow<List<PasswordProperties>>,
                  onEmailChanged: (String) -> Unit,
                  onPasswordChanged: (String) -> Unit,
                  onTermsChanged:(Boolean)->Unit,
                  onBackIsPressed: () -> Unit,
                  onSignUp:()->Unit,
                  ) {

    var isCompleted by rememberSaveable { mutableStateOf(false)}
    val termsIsChecked by termsIsChecked.collectAsState(initial = false)
    var showPolicy by remember { mutableStateOf(false) }
    val termsState by termState.collectAsState(initial = UIState.Enabled)
    val currentError by errorChannel.collectAsState(initial = null)
    val context = LocalContext.current

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
            //
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom)
            ) {

                Headline(onBackIsPressed)
                Image(
                    painter = painterResource(id = R.drawable.credentials_image),
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .fillMaxHeight(0.4f)
                        .fillMaxWidth(0.75f)
                        .padding(16.dp)
                )
                EmailField(
                    formState = emailFormState,
                    onValueChange = onEmailChanged,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
                PasswordFieldWithStrengthMeter(
                    formState = passwordFormState,
                    onValueChange = onPasswordChanged,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    currentProperties = passwordProperties
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable {
                            showPolicy = !showPolicy
                        },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val color by animateColorAsState(targetValue = if (termsState.isError()) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground)
                    Checkbox(
                        checked = termsIsChecked,
                        enabled = false,
                        onCheckedChange = onTermsChanged,
                        colors = CheckboxDefaults.colors(
                            uncheckedColor = if (termsState.isError()) color else MaterialTheme.colorScheme.surface,
                            checkedColor = if (termsState.isError()) color else MaterialTheme.colorScheme.secondary,
                            checkmarkColor = if (termsState.isError()) MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onSecondary
                        )
                    )
                    Text("I agree with the Terms of Use", color = color)

                }
                RegisterButton(
                    state = ctaState, onClick = { onSignUp() }, onSuccess = {
                        isCompleted = true
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                )
            }
        }



    AnimatedVisibility(visible = showPolicy) {
        TermsOfUseDialogue(onDismiss = { showPolicy = false }, onAgreed = {
            showPolicy = false
            onTermsChanged(true)
        }, onRejected = {
            showPolicy = false
            onTermsChanged(false)
        })
    }

    LaunchedEffect(key1 = currentError) {
        Log.d("Test", "Launched Effect executed")
        currentError?.let { id ->
            when (id) {

                in listOf(R.string.registration_unknown_error) -> {
                    Toast.makeText(context, context.getString(id), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}



@Composable
private fun Headline(onBackIsPressed:()->Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBackIosNew,
            contentDescription = null,
            modifier = Modifier.clickable { onBackIsPressed() })
        Text(
            text = "Create Your Account",
            style = Typography.headlineMedium,
            textAlign = TextAlign.End
        )
        Text(
            text = "You will be done in no time",
            style = Typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(0.75f),
            textAlign = TextAlign.End
        )
    }
}



@Composable
private fun RegisterButton(state: Flow<UIState>, onClick:()->Unit, onSuccess:()->Unit,modifier: Modifier) {

    val currentState by state.onEach {
        if (it.isCompleted()) {
            delay(200)
            onSuccess()
        }
    }.collectAsState(initial = UIState.Enabled)

    val containerColor by animateColorAsState(
        targetValue = when {
            currentState.isCompleted() -> MaterialTheme.colorScheme.secondaryContainer
            currentState.isError() -> MaterialTheme.colorScheme.errorContainer
            else -> MaterialTheme.colorScheme.primary
        }
    )
    val textColor by animateColorAsState(
        targetValue = when {
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
                enabled = !currentState.isLoading(),
                colors = ButtonColors(
                    containerColor = containerColor,
                    contentColor = textColor,
                    disabledContainerColor = containerColor,
                    disabledContentColor = textColor
                )
            ) {
                Text(
                    text = when {
                        currentState.isError() -> "Retry"
                        currentState.isEnabled() -> "Register Now!"
                        else -> "Account Created!"
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                Icon(
                    imageVector = when {
                        currentState.isError() -> Icons.Filled.Refresh
                        currentState.isEnabled() -> Icons.Filled.Login
                        else -> Icons.Filled.CheckCircle
                    }, contentDescription = null, tint = iconColor
                )
            }
        }
    }
}

