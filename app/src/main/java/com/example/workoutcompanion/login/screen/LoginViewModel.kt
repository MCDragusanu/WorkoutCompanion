package com.example.workoutcompanion.login.screen

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.R
import com.example.workoutcompanion.common.FormState
import com.example.workoutcompanion.common.NetworkObserver
import com.example.workoutcompanion.common.UIState
import com.example.workoutcompanion.common.use_cases.email.EmailProperties
import com.example.workoutcompanion.common.use_cases.email.ValidateEmail
import com.example.workoutcompanion.login.auth_service.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LoginViewModel @Inject constructor(
    @Named("authTestImpl")
    private val auth : AuthManager, private val networkObserver: NetworkObserver
                                         ):ViewModel() {


    private val _emailFormState = MutableStateFlow(FormState())
    val emailFormState = _emailFormState.asStateFlow()

    private val _passwordFormState = MutableStateFlow(FormState())
    val passwordFormState = _passwordFormState.asStateFlow()

    private val _ctaState = MutableStateFlow(UIState.Enabled)
    val ctaState = _ctaState.asStateFlow()

    private val _errorFlow = MutableSharedFlow<ErrorMessage?>()
    val errorChannel = _errorFlow.asSharedFlow()

    private val _hasConnection = networkObserver.observe().onEach {
        if(it.isOnline()) {
            putUiInEnabledState()
        }
        }.map { it.isOnline() }.stateIn(viewModelScope, started = SharingStarted.Eagerly, true)


    private var onLoginCompleted: (suspend (String) -> Unit)? = null

    fun setLoginCallback(callback: suspend (String) -> Unit) {
        this.onLoginCompleted = callback
    }

    private fun onError(throwable: Throwable) {
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
                    _ctaState.emit(UIState.Disbled)
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

    fun onLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            val isValid = checkInput()
            val isOnline = _hasConnection.value
            if(!isOnline){
                onError(AuthManager.Errors.NoInternetConnection)
            }
            if (isValid and isOnline) auth.loginUser(
                _emailFormState.value.text,
                _passwordFormState.value.text,
                onLoading = {
                    putUiInLoadingState()
                },
                onError = {
                    onError(it)
                },
                onSuccess = { uid ->
                    putUiInCompletedState()
                    viewModelScope.launch(Dispatchers.Main) {
                        onLoginCompleted?.let {
                            delay(250)
                            it.invoke(uid)
                        }
                    }
                }
            )
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

    class ErrorMessage(@StringRes val messageUid: Int)
}