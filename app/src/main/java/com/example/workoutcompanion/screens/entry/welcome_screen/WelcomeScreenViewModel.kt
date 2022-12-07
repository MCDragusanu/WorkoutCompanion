package com.example.workoutcompanion.screens.entry.welcome_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.common.UIState
import com.example.workoutcompanion.auth.AuthService
import com.example.workoutcompanion.auth.AuthServiceImpl
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WelcomeScreenViewModel:ViewModel() {
    private val auth = AuthServiceImpl()

    private var _txtEmail = mutableStateOf("")
    private var _txtPassword = mutableStateOf("")
    var txtEmail: State<String> = _txtEmail
    var txtPassword: State<String> = _txtPassword



    private var _error = MutableStateFlow<WelcomeScreenState.Error?>(null)
    var error = _error.asStateFlow()

    private var _emailState = MutableStateFlow(UIState.Enabled)
    var emailState = _emailState.asStateFlow()

    private var _passwordState = MutableStateFlow(UIState.Enabled)
    var passwordState = _passwordState.asStateFlow()

    var _loginFlow = MutableStateFlow<UIState>(UIState.Enabled)
    var loginFlow = _loginFlow.asSharedFlow()

    private val _resetPasswordState = MutableStateFlow(UIState.Enabled)
    var resetPasswordUIState = _resetPasswordState.asStateFlow()

    private val _resetPasswordError = MutableStateFlow("")
    var resetPasswordErrorMessage = _resetPasswordError.asStateFlow()

    fun onEmailChanged(newEmail: String) {
        _txtEmail.value = newEmail
        txtEmail = _txtEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _txtPassword.value = newPassword
        txtPassword = _txtPassword
    }

    fun onLogin() {
        viewModelScope.launch(IO) {
            if (checkInput()) {
                auth.logUserWithEmail(
                    _txtEmail.value,
                    _txtPassword.value,
                    callback = object : AuthService.EventCallbacks {
                        override suspend fun onStart() {
                            onStartLogin()
                        }
                        override suspend fun onSuccess(firebaseId:String) {
                            onSuccessLogin()

                        }
                        override suspend fun onError(exception: Exception) {
                            onErrorLogin(exception)
                        }
                    }
                )
            }
        }
    }

    suspend fun checkInput(): Boolean {
        val result = _txtPassword.value.isNotEmpty() && _txtEmail.value.isNotEmpty()
        val emailNewState = if (_txtEmail.value.isEmpty()) UIState.Error else UIState.Enabled
        val passwordNewState = if (_txtPassword.value.isEmpty()) UIState.Error else UIState.Enabled
        _passwordState.emit(passwordNewState)
        _emailState.emit(emailNewState)
        emailState = _emailState
        passwordState = _passwordState
        _error.value = WelcomeScreenState.Error.EmptyField

        return result
    }

    suspend fun onStartLogin() {
        _loginFlow.emit(UIState.Loading)
        loginFlow = _loginFlow
    }

    suspend fun onSuccessLogin() {
        _emailState.value = (UIState.Completed)
        _passwordState.value = (UIState.Completed)
        _loginFlow.emit(UIState.Completed)
        emailState = _emailState
        passwordState = _passwordState
        loginFlow = _loginFlow
    }

    suspend fun onErrorLogin(exception: Exception) {
        _loginFlow.emit(UIState.Error)
        when (exception) {
            is FirebaseAuthInvalidUserException -> {
                _emailState.emit(UIState.Error)
                _passwordState.emit(UIState.Enabled)
                _error.emit(WelcomeScreenState.Error.UserNotFound)
            }
            is FirebaseAuthInvalidCredentialsException -> {
                when (exception.errorCode) {
                    "ERROR_INVALID_EMAIL" -> {
                        _emailState.emit(UIState.Error)
                        _passwordState.emit(UIState.Enabled)
                        _error.emit( WelcomeScreenState.Error.InvalidEmailAddress)
                    }
                    "ERROR_WRONG_PASSWORD" -> {
                        _passwordState.emit(UIState.Error)
                        _error.emit ( WelcomeScreenState.Error.PasswordInvalid)
                    }
                }
            }
            else -> {
                if (!auth.checkEmailIsValid(txtEmail.value)) {
                    _error.emit(  WelcomeScreenState.Error.InvalidEmailAddress)
                    _emailState.emit(UIState.Error)
                } else {
                    _loginFlow.emit(UIState.Error)
                    _error.value = WelcomeScreenState.Error.UnknownError.apply {
                        this.message = exception.message!!
                    }

                }
            }
        }
        emailState = _emailState
        passwordState = _passwordState
        loginFlow = _loginFlow.asSharedFlow()
        error = _error
    }

    fun sendForgotPasswordEmail(email:String) {
        viewModelScope.launch(IO) {
               auth.sendForgotPasswordEmail(email , object :AuthService.EventCallbacks{
                   override suspend fun onStart() {
                       onResetPasswordEmailSent()
                   }

                   override suspend fun onSuccess(firebaseId:String) {
                       onResetPasswordEmailSuccess()
                   }



                   override suspend fun onError(exception: Exception) {
                       onResetPasswordEmailError(exception)
                   }

               })
        }

    }
    private suspend fun onResetPasswordEmailSuccess() {
        _resetPasswordState.emit(UIState.Completed)
        resetPasswordUIState = _resetPasswordState.asStateFlow()
        delay(250)
    }

    private suspend fun onResetPasswordEmailError(exception: Exception) {
        _resetPasswordError.emit(exception.message?:"Unknwown Error has occurred :( ")
        resetPasswordErrorMessage = _resetPasswordError.asStateFlow()
        _resetPasswordState.emit(UIState.Error)
        resetPasswordUIState = _resetPasswordState.asStateFlow()
    }

    private suspend fun onResetPasswordEmailSent() {
        _resetPasswordState.emit(UIState.Loading)
        resetPasswordUIState = _resetPasswordState.asStateFlow()
    }

    fun onRestartFlow() {
        viewModelScope.launch {
            _resetPasswordState.emit(UIState.Enabled)
            _resetPasswordError.emit("")
            resetPasswordErrorMessage =_resetPasswordError.asStateFlow()
            resetPasswordUIState = _resetPasswordState.asStateFlow()
        }
    }


}