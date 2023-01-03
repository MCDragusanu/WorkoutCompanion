package com.example.workoutcompanion.presentation.graphs.entry.screens.welcome

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.workoutcompanion.auth.AuthService
import com.example.workoutcompanion.auth.AuthServiceImpl
import com.example.workoutcompanion.presentation.ui_state.UIState
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.Exception

class WelcomeScreenViewModel:ViewModel(), AuthService.EventCallbacks {
    private var TAG = "AuthService"
    private var _uiState = MutableStateFlow(WelcomeScreenState())
    var uiState = _uiState.asStateFlow()
        private set

    fun onEmailChanged(newEmail: String) {
        this._uiState.value = _uiState.value.copy(
            txtEmail = newEmail,
            errorEmail = if (newEmail.isNotEmpty()) null else WelcomeScreenState.Error.EmptyEmail,
            etEmailState = if(this._uiState.value.errorEmail!=null) UIState.Error else UIState.Default
        )
        uiState = _uiState.asStateFlow()
    }

    fun onPasswordChanged(newPassword: String) {
        this._uiState.value = _uiState.value.copy(
            txtPassword = newPassword,
            errorPassword = if (newPassword.isNotEmpty()) null else WelcomeScreenState.Error.EmptyPassword,
            etPasswordState = if(this._uiState.value.errorPassword!=null) UIState.Error else UIState.Default
        )
        uiState = _uiState.asStateFlow()
    }

    fun onLoginUserWithEmail() {
        val emailValid = uiState.value.txtEmail.isNotEmpty()
        val passwordValid = uiState.value.txtPassword.isNotEmpty()
        _uiState.value = uiState.value.copy(
            btnLoginState = if (emailValid && passwordValid) UIState.Default else UIState.Error,
            etEmailState = if (!emailValid) UIState.Error else UIState.Default,
            etPasswordState = if (!passwordValid) UIState.Error else UIState.Default,
            isLoading = true,
            errorPassword = if (!passwordValid) WelcomeScreenState.Error.EmptyPassword else null,
            errorEmail = if (!emailValid) WelcomeScreenState.Error.EmptyEmail else null,
        )
        uiState = _uiState.asStateFlow()

        if (emailValid && passwordValid) {
            AuthServiceImpl().logUserWithEmail(
                uiState.value.txtEmail,
                uiState.value.txtPassword,
                callback = this
            )
        }
    }


    private fun checkEmail() = AuthServiceImpl().checkEmailIsValid(uiState.value.txtEmail)

    override fun onStart() {
        Log.d(TAG, "Login Started in ViewModel")
        _uiState.value = uiState.value.copy(
            isLoading = true,
            etEmailState = UIState.Default,
            etPasswordState = UIState.Default,
            btnLoginState = UIState.Loading
        )
        uiState = _uiState.asStateFlow()
    }

    override fun onSuccess() {
        Log.d(TAG, "Login Success in ViewModel")
        _uiState.value = uiState.value.copy(
            isLoading = false,
            btnLoginState = UIState.Completed,
            etEmailState = UIState.Completed,
            etPasswordState = UIState.Completed
        )
        uiState = _uiState.asStateFlow()

    }

    override fun onError(exception: Exception) {
        Log.d(TAG, "Login Error in ViewModel with exception = ${exception.localizedMessage}")
        when {
            exception is FirebaseAuthInvalidUserException -> {
                _uiState.value = uiState.value.copy(
                    btnLoginState = UIState.Error,
                    etEmailState = UIState.Error,
                    etPasswordState = UIState.Default,
                    errorEmail = WelcomeScreenState.Error.NoUserFound,
                    isLoading = false
                )
            }
            exception is FirebaseAuthInvalidCredentialsException -> {
                _uiState.value = uiState.value.copy(
                    btnLoginState = UIState.Error,
                    etEmailState = UIState.Default,
                    etPasswordState = UIState.Error,
                    errorPassword = WelcomeScreenState.Error.PasswordInvalid,
                    isLoading = false
                )
            }
            !checkEmail() -> {
                _uiState.value = uiState.value.copy(
                    btnLoginState = UIState.Error,
                    etEmailState = UIState.Error,
                    etPasswordState = UIState.Default,
                    errorPassword = WelcomeScreenState.Error.EmailInvalid,
                    isLoading = false
                )
            }
            else -> {
                _uiState.value = uiState.value.copy(
                    btnLoginState = UIState.Error,
                    etEmailState = UIState.Default,
                    etPasswordState = UIState.Default,
                    errorPassword = null,
                    errorEmail = null,
                    unknownError = WelcomeScreenState.Error.UnknownError.apply {
                        this.message =
                            exception.localizedMessage
                                ?: "Unknown Error has occurred while logging in :("
                    },
                    isLoading = false
                )

            }
        }
        Log.d(
            TAG,
            "Login errorMessage in ViewModel = ${_uiState.value.errorPassword?.message ?: "No Error for Password"}, ${_uiState.value.errorEmail?.message ?: "No Error for Email"},${_uiState.value.unknownError?.message ?: "No Unknown Error"}"
        )
        uiState = _uiState.asStateFlow()
    }
}