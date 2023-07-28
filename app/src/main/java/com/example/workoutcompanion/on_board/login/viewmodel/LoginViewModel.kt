package com.example.workoutcompanion.on_board.login.viewmodel

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.R
import com.example.workoutcompanion.common.composables.FormState
import com.example.workoutcompanion.common.NetworkObserver
import com.example.workoutcompanion.common.composables.UIState
import com.example.workoutcompanion.common.use_cases.email.EmailProperties
import com.example.workoutcompanion.common.use_cases.email.ValidateEmail
import com.example.workoutcompanion.core.data.di.Testing
import com.example.workoutcompanion.core.data.auth_service.AuthManager
import com.example.workoutcompanion.core.data.di.Production
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseRepository
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @Testing
    private val auth : AuthManager,
    @Testing
    private val repository : ExerciseRepository,
    @Testing
    private val userRepository : ProfileRepository,

    private val networkObserver: NetworkObserver
                                         ):ViewModel() {


    private val _emailFormState = MutableStateFlow(FormState())
    val emailFormState = _emailFormState.asStateFlow()

    private val _passwordFormState = MutableStateFlow(FormState())
    val passwordFormState = _passwordFormState.asStateFlow()

    private val _ctaState = MutableStateFlow(UIState.Enabled)
    val ctaState = _ctaState.asStateFlow()

    private val _errorFlow = MutableSharedFlow<ErrorMessage?>()
    val errorChannel = _errorFlow.asSharedFlow()


    private val _resetPasswordEmailFormState = MutableStateFlow(FormState())
    val resetPasswordFormState = _resetPasswordEmailFormState.asStateFlow()

    private val _resetPasswordCtaState = MutableStateFlow(UIState.Enabled)
    val resetPasswordCtaState = _resetPasswordCtaState.asStateFlow()


    private lateinit var onLoginCompleted:  (String) -> Unit

    fun setLoginCallback(callback:  (String) -> Unit) {
        this.onLoginCompleted = callback
    }

    private fun onError(throwable: Exception) {
        throwable.printStackTrace()
        viewModelScope.launch {
            when (throwable) {

                is AuthManager.Errors.NoInternetConnection -> {
                    Log.d("Test", "No Internet Connection")
                    _passwordFormState.update {
                        it.copy(
                            state = UIState.Enabled,
                            errorStringResource = null
                        )
                    }
                    _emailFormState.update {
                        it.copy(
                            state = UIState.Enabled,
                            errorStringResource = null
                        )
                    }
                    _errorFlow.emit(ErrorMessage(R.string.error_no_internet_connection))
                    _ctaState.emit(UIState.Enabled)
                }
                is AuthManager.Errors.InvalidEmail -> {
                    _emailFormState.update {
                        it.copy(
                            errorStringResource = R.string.login_invalid_email,
                            state = UIState.Error
                        )
                    }
                    _passwordFormState.update {
                        it.copy(
                            errorStringResource = null,
                            state = UIState.Enabled
                        )
                    }
                    _ctaState.emit(UIState.Error)
                }
                is AuthManager.Errors.NoEmailProvided -> {
                    _emailFormState.update {
                        it.copy(
                            errorStringResource = R.string.login_empty_email,
                            state = UIState.Error
                        )
                    }
                    _passwordFormState.update {
                        it.copy(
                            errorStringResource = null,
                            state = UIState.Enabled
                        )
                    }
                    _ctaState.emit(UIState.Error)
                }
                is AuthManager.Errors.NoAccountFound -> {
                    _emailFormState.update {
                        it.copy(
                            errorStringResource = R.string.login_account_not_found,
                            state = UIState.Error
                        )
                    }
                    _passwordFormState.update {
                        it.copy(
                            errorStringResource = null,
                            state = UIState.Enabled
                        )
                    }
                    _ctaState.emit(UIState.Error)
                }
                is AuthManager.Errors.InvalidCredentials -> {
                    _emailFormState.update {
                        it.copy(
                            errorStringResource = null,
                            state = UIState.Error
                        )
                    }
                    _passwordFormState.update {
                        it.copy(
                            errorStringResource = R.string.login_credentials_invalid,
                            state = UIState.Error
                        )
                    }
                    _ctaState.emit(UIState.Error)
                }
                is AuthManager.Errors.NoPasswordProvided -> {
                    _emailFormState.update {
                        it.copy(
                            errorStringResource = null,
                            state = UIState.Enabled
                        )
                    }
                    _passwordFormState.update {
                        it.copy(
                            errorStringResource = R.string.login_empty_password,
                            state = UIState.Error
                        )
                    }
                    _ctaState.emit(UIState.Error)
                }
                else -> {
                    _errorFlow.emit(ErrorMessage(R.string.login_unknown_error))
                    _emailFormState.update {
                        it.copy(
                            errorStringResource = null,
                            state = UIState.Enabled
                        )
                    }
                    _passwordFormState.update {
                        it.copy(
                            errorStringResource = null,
                            state = UIState.Enabled
                        )
                    }
                    _ctaState.emit(UIState.Error)
                }
            }

        }
    }

    fun onSendResetPasswordEmail(onCompletedAction:()->Unit) {
        viewModelScope.launch {
            if (_resetPasswordEmailFormState.value.text.isEmpty()) {
                _resetPasswordEmailFormState.update {
                    it.copy(
                        errorStringResource = R.string.login_empty_email ,
                        state = UIState.Error
                    )
                }
                return@launch
            }
            if (ValidateEmail().execute(_resetPasswordEmailFormState.value.text) == EmailProperties.Invalid) {
                _resetPasswordEmailFormState.update {
                    it.copy(
                        errorStringResource = R.string.error_invalid_email ,
                        state = UIState.Error
                    )
                }
                return@launch
            }
            if (!networkObserver.getStatus()) {
                _resetPasswordEmailFormState.update {
                    it.copy(
                        errorStringResource = R.string.error_no_internet_connection ,
                        state = UIState.Enabled
                    )
                }
                return@launch
            }
            auth.sendResetPassword(_resetPasswordEmailFormState.value.text , onLoading = {
                _resetPasswordCtaState.update { UIState.Loading }
                _resetPasswordEmailFormState.update {
                    it.copy(errorStringResource = null)
                }
            } , onError = {
                _resetPasswordCtaState.update { UIState.Error }
                handleResetPasswordError(it)
            } , onSuccess = {
                _resetPasswordCtaState.update {
                    UIState.Completed
                }
                viewModelScope.launch(Dispatchers.Main) {
                    delay(500)
                    onCompletedAction()
                }
            })
        }
    }

    private fun handleResetPasswordError(it : Exception) {
        when(it) {
            is AuthManager.Errors.NoAccountFound -> {
                _resetPasswordEmailFormState.update {
                    it.copy(
                        errorStringResource = R.string.login_account_not_found ,
                        state = UIState.Error
                    )
                }
            }
            is AuthManager.Errors.InvalidEmail -> {
                _resetPasswordEmailFormState.update {
                    it.copy(
                        errorStringResource = R.string.login_invalid_email,
                        state = UIState.Error
                    )
                }
            }
        }
    }

    fun onLogin() {
        // Launch a coroutine in the IO dispatcher
        viewModelScope.launch(Dispatchers.IO) {

            putUiInEnabledState()
            // Observe network connection state
            val isOnline = networkObserver.getStatus()
            if (!isOnline) onError(AuthManager.Errors.NoInternetConnection)
            else {
                // Check if the input is valid
                val isValid = checkInput()
                if (isValid) {
                    // Call the loginUser method of the auth module
                    auth.loginUser(
                        _emailFormState.value.text ,
                        _passwordFormState.value.text ,
                        onLoading = {
                            putUiInLoadingState()
                        } ,
                        onError = { onError(it) } ,
                        onSuccess = { uid ->
                            // Put the UI in the completed state
                            viewModelScope.launch(Dispatchers.IO) {

                                    userRepository.getProfileFromCloudSource(uid , this).onFailure {
                                        onError(Exception(it))
                                    }.onSuccess {
                                        Log.d(
                                            "Test" ,
                                            "Login ViewModel :: completed retrieval of profile from cloud"
                                        )
                                        it?.let {
                                            userRepository.updateLocalProfile(it.uid , it)
                                                .onFailure {
                                                    onError(Exception(it))
                                                }.onSuccess {
                                                    Log.d(
                                                        "Test" ,
                                                        "Login ViewModel:: local profile has been updated"
                                                    )
                                                }
                                        }
                                    }

                                val res =  repository.onUpdateLocalDatabase()
                                if (res.isFailure) {
                                    res.exceptionOrNull()
                                        ?: Exception("Unknown error has occurred while caching database")
                                } else {
                                    withContext(Dispatchers.Main) {
                                        putUiInCompletedState()
                                        delay(500)
                                        onLoginCompleted.invoke(uid)
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    private fun putUiInLoadingState() {
        _emailFormState.update {
            it.copy(
                errorStringResource = null,
                state = UIState.Loading
            )
        }
        _passwordFormState.update {
            it.copy(
                errorStringResource = null,
                state = UIState.Loading
            )
        }
        _ctaState.update {
            UIState.Loading
        }
    }

    private fun putUiInCompletedState() {
        _emailFormState.update {
            it.copy(
                errorStringResource = null,
                state = UIState.Completed
            )
        }
        _passwordFormState.update {
            it.copy(
                errorStringResource = null,
                state = UIState.Completed
            )
        }
        _ctaState.update {
            UIState.Completed
        }
    }

    private fun putUiInEnabledState(){
        _emailFormState.update { it.copy(state = UIState.Enabled)}
        _passwordFormState.update { it.copy(state = UIState.Enabled) }
        _ctaState.update { UIState.Enabled }
    }

    fun onEmailChanged(string: String) {
        _emailFormState.update { it.copy(text = string) }

    }

    fun onPasswordChanged(string: String) {
        _passwordFormState.update { it.copy(text = string) }

    }

    private fun checkInput(): Boolean {
        val emailState = ValidateEmail().execute(_emailFormState.value.text)
        when (emailState) {
            EmailProperties.Empty -> {
                onError(AuthManager.Errors.NoEmailProvided)
                return false
            }
            EmailProperties.Invalid -> {
                onError(AuthManager.Errors.InvalidEmail)
                return false
            }
            EmailProperties.Valid -> {
                _emailFormState.update {
                    it.copy(
                        errorStringResource = null,
                        state = UIState.Enabled
                    )
                }
            }
        }
        if (_passwordFormState.value.text.isBlank()) {
            onError(AuthManager.Errors.NoPasswordProvided)
            return false
        }

        return _passwordFormState.value.text.isNotEmpty() && emailState == EmailProperties.Valid
    }

    fun onResetPasswordEmailChanged(email : String) {
        _resetPasswordEmailFormState.update { it.copy(text = email) }
    }

    fun resetDialogueState() {
        _resetPasswordCtaState.update { UIState.Enabled }
        _resetPasswordEmailFormState.update { it.copy(text = "" ,errorStringResource = null , state = UIState.Enabled) }
    }

    class ErrorMessage(@StringRes val messageUid: Int)
}