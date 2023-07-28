package com.example.workoutcompanion.on_board.registration.screens.account

import android.util.Log
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.compose.getPalette
import com.example.workoutcompanion.R
import com.example.workoutcompanion.common.composables.FormState
import com.example.workoutcompanion.common.composables.UIState
import com.example.workoutcompanion.common.composables.AnimatedStatefulCallToAction
import com.example.workoutcompanion.common.composables.EmailField
import com.example.workoutcompanion.common.composables.PasswordFieldWithStrengthMeter
import com.example.workoutcompanion.common.use_cases.password.PasswordProperties
import com.example.workoutcompanion.on_board.registration.dialogue.TermsOfUseDialogue
import com.example.workoutcompanion.on_board.composables.FormField
import com.example.workoutcompanion.ui.Typography
import com.example.workoutcompanion.ui.cardShapes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun AccountScreen(emailFormState:Flow<FormState> ,
                  passwordFormState:Flow<FormState> ,
                  firstNameFormState:Flow<FormState> ,
                  lastNameFormState:Flow<FormState> ,
                  termsIsChecked:Flow<Boolean> ,
                  termState:Flow<UIState> ,
                  ctaState:Flow<UIState> ,
                  errorChannel:Flow<Int?> ,
                  passwordProperties: Flow<List<PasswordProperties>> ,
                  onEmailChanged: (String) -> Unit ,
                  onPasswordChanged: (String) -> Unit ,
                  onFirstNameChanged:(String)->Unit ,
                  onLastNameChanged:(String)->Unit ,
                  onTermsChanged:(Boolean)->Unit ,
                  onBackIsPressed: () -> Unit ,
                  onSignUp:()->Unit ,
                  ) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var isCompleted by rememberSaveable { mutableStateOf(false) }

    var showPolicy by remember { mutableStateOf(false) }
    val termsState by termState.collectAsState(initial = UIState.Enabled)
    val currentError by errorChannel.collectAsState(initial = null)
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) ,
        snackbarHost = { SnackbarHost(snackBarHostState) }
        //
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp , vertical = 16.dp)
                .verticalScroll(rememberScrollState()) ,
            horizontalAlignment = Alignment.CenterHorizontally ,
            verticalArrangement = Arrangement.spacedBy(8.dp , Alignment.Bottom)
        ) {

            Headline(onBackIsPressed)
            Image(
                painter = painterResource(id = R.drawable.credentials_image) ,
                contentDescription = null ,
                contentScale = ContentScale.Inside ,
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .fillMaxWidth(0.75f)
                    .padding(vertical = 48.dp , horizontal = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight() ,
                verticalAlignment = Alignment.CenterVertically ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FormField(
                    modifier = Modifier
                        .wrapContentHeight()
                        .weight(1f , true) ,
                    withErrorMessage = true ,
                    formStateFlow = firstNameFormState
                ) { flow , modifier ->
                    val state = flow.collectAsState(initial = FormState())
                    OutlinedTextField(
                        value = state.value.text ,
                        onValueChange = onFirstNameChanged ,
                        label = {
                            Text(text = "First Name")
                        })
                }
                FormField(
                    modifier = Modifier
                        .wrapContentHeight()
                        .weight(1f , true) ,
                    withErrorMessage = true ,
                    formStateFlow = lastNameFormState
                ) { flow , modifier ->
                    val state = flow.collectAsState(initial = FormState())
                    OutlinedTextField(
                        value = state.value.text ,
                        onValueChange = onLastNameChanged ,
                        label = {
                            Text(text = "Last Name")
                        })
                }
            }
            EmailField(
                formState = emailFormState ,
                onValueChange = onEmailChanged ,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            PasswordFieldWithStrengthMeter(
                formState = passwordFormState ,
                onValueChange = onPasswordChanged ,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight() ,
                currentProperties = passwordProperties
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable {
                        showPolicy = !showPolicy
                    } ,
                horizontalArrangement = Arrangement.Start ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val containerColor by animateColorAsState(
                    targetValue = if (termsState.isCompleted()) getPalette().current.successContainer else if (termsState.isError()) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surface
                )
                val contentColor by animateColorAsState(targetValue = if (termsState.isCompleted()) getPalette().current.onSuccessContainer else if (termsState.isError()) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSurface)


                Surface(
                    modifier = Modifier.wrapContentSize(align = Alignment.Center) ,
                    // border = BorderStroke(3.dp , containerColor) ,
                    color = containerColor ,
                    shape = cardShapes.extraSmall ,
                ) {
                    AnimatedVisibility(visible = termsState.isCompleted()) {
                        Icon(
                            imageVector = Icons.Filled.Check ,
                            contentDescription = null ,
                            tint = contentColor ,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    AnimatedVisibility(visible = termsState.isError()) {
                        Icon(
                            imageVector = Icons.Filled.WarningAmber ,
                            contentDescription = null ,
                            tint = contentColor ,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    if (termsState.isError()) "You must agree with he terms in order to continue" else "I agree with the Terms of Use" ,
                    color = contentColor
                )

            }
            RegisterButton(
                state = ctaState , onClick = { onSignUp() } , onSuccess = {
                    isCompleted = true
                } , modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            )
        }
    }



    AnimatedVisibility(visible = showPolicy) {
        TermsOfUseDialogue(onDismiss = { showPolicy = false } , onAgreed = {
            showPolicy = false
            onTermsChanged(true)
        } , onRejected = {
            showPolicy = false
            onTermsChanged(false)
        })
    }

    LaunchedEffect(key1 = currentError) {
        Log.d("Test" , "Error changed")
        currentError?.let {
            if (it in listOf(
                    R.string.registration_unknown_error ,
                    R.string.error_no_internet_connection
                )
            ) {
                scope.launch {
                    snackBarHostState.showSnackbar(context.getString(it))
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
private fun RegisterButton(state: Flow<UIState> , onClick:()->Unit , onSuccess:()->Unit , modifier: Modifier) {

    val currentState by state.collectAsState(initial = UIState.Enabled)


    AnimatedStatefulCallToAction(
        ctaState = state ,
        modifier = modifier ,
        action = onClick,
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
                        currentState.isEnabled() -> "Create your Account!"
                        else -> "Account Created"
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

