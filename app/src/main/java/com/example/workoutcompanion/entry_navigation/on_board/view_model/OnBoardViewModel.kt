package com.example.workoutcompanion.entry_navigation.on_board.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.R
import com.example.workoutcompanion.common.composables.FormState
import com.example.workoutcompanion.common.NetworkObserver
import com.example.workoutcompanion.common.composables.UIState
import com.example.workoutcompanion.common.use_cases.email.EmailProperties
import com.example.workoutcompanion.common.use_cases.email.ValidateEmail
import com.example.workoutcompanion.common.use_cases.password.PasswordProperties
import com.example.workoutcompanion.common.use_cases.password.ValidatePassword

import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseRepositoryImpl

import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepositoryImpl
import com.example.workoutcompanion.core.data.auth_service.AuthManager
import com.example.workoutcompanion.core.data.di.Production
import com.example.workoutcompanion.core.data.di.Testing
import com.example.workoutcompanion.core.data.exercise_database.common.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    @Testing()
    private val repository : ExerciseRepository ,

    @Testing()
    private val authManager : AuthManager ,

    @Testing()
    private val userRepo : ProfileRepositoryImpl ,


    private val networkObserver : NetworkObserver) : ViewModel() {

    private val _passwordProperties = MutableStateFlow<List<PasswordProperties>>(emptyList())
    val passwordProperties = _passwordProperties.asStateFlow()

    private val _emailFormState = MutableStateFlow(FormState())
    val emailFormState = _emailFormState.asStateFlow()

    private val _firstNameFormState = MutableStateFlow(FormState())
    val firstNameFormState =_firstNameFormState.asStateFlow()

    private val _lastNameFormState = MutableStateFlow(FormState())
    val lastNameFormState = _lastNameFormState.asStateFlow()

    private val _passwordFormState = MutableStateFlow(FormState())
    val passwordFormState = _passwordFormState.asStateFlow()

    private val _ctaState = MutableStateFlow(UIState.Enabled)
    val ctaState = _ctaState.asStateFlow()

    private val _termsIsChecked = MutableStateFlow(false)
    val termsIsChecked = _termsIsChecked.asStateFlow()

    private val _termsState = MutableStateFlow(UIState.Enabled)
    val termsState = _termsState.asStateFlow()

    private val _errorFlow = MutableSharedFlow<Int?>()
    val errorChannel = _errorFlow.asSharedFlow()



    private val _userInformation = mutableMapOf<String, String>()

    private var navigateToNextScreen : (suspend (String) -> Unit)? = null

    fun setNavigationCallback(callback : suspend (String) -> Unit) {
        this.navigateToNextScreen = callback
    }

    fun getUserUid():String = _userInformation["userUid"]?:"DEFAULT_USER_UID"

    private fun onError(throwable : Throwable) {
        throwable.printStackTrace()
        viewModelScope.launch {
            when (throwable) {
                is AuthManager.Errors.NoInternetConnection -> {
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
                    _errorFlow.emit(R.string.error_no_internet_connection)

                    _ctaState.update { UIState.Enabled }
                }
                is AuthManager.Errors.TermsNotChecked -> {
                    _emailFormState.update {
                        it.copy(
                            state = UIState.Enabled,
                            errorStringResource = null
                        )
                    }
                    _passwordFormState.update {
                        it.copy(state = UIState.Enabled, errorStringResource = null)
                    }
                    _termsState.update { UIState.Error }
                }
                is AuthManager.Errors.InvalidEmail -> {
                    _emailFormState.update {
                        it.copy(
                            errorStringResource = R.string.registration_invalid_email,
                            state = UIState.Error
                        )
                    }
                    _passwordFormState.update {
                        it.copy(
                            errorStringResource = null,
                            state = UIState.Enabled
                        )
                    }
                }
                is AuthManager.Errors.NoEmailProvided -> {
                    _emailFormState.update {
                        it.copy(
                            errorStringResource = R.string.registration_empty_email,
                            state = UIState.Error
                        )
                    }
                    _passwordFormState.update {
                        it.copy(
                            errorStringResource = null,
                            state = UIState.Enabled
                        )
                    }

                }
                is AuthManager.Errors.AccountCollision -> {
                    _emailFormState.update {
                        it.copy(
                            errorStringResource = R.string.registration_account_collision,
                            state = UIState.Error
                        )
                    }
                    _passwordFormState.update {
                        it.copy(
                            errorStringResource = null,
                            state = UIState.Enabled
                        )
                    }
                }
                is AuthManager.Errors.PasswordTooWeak -> {
                    _emailFormState.update {
                        it.copy(
                            errorStringResource = null,
                            state = UIState.Enabled
                        )
                    }
                    _passwordFormState.update {
                        it.copy(
                            errorStringResource = R.string.registration_password_too_weak,
                            state = UIState.Error
                        )
                    }

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
                            errorStringResource = R.string.registration_empty_password,
                            state = UIState.Error
                        )
                    }

                }
                else -> {
                    _errorFlow.emit(R.string.registration_unknown_error)
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
            _ctaState.emit(UIState.Error)
        }
    }

    fun onSignUp() {
        viewModelScope.launch(Dispatchers.IO) {

            val isOnline = networkObserver.getStatus()

            if (!isOnline) onError(AuthManager.Errors.NoInternetConnection)

            else {
                val isValid = checkInput()

                if (isValid) authManager.registerUser(
                    _emailFormState.value.text ,
                    _passwordFormState.value.text ,
                    onLoading = {
                        putUiInLoadingState()
                    } ,
                    onError = { onError(it) } ,
                    onSuccess = { uid ->
                        putUiInCompletedState()
                        saveUserInfo(uid)
                        cacheExerciseDatabase(onComplete = { navigateToNextScreen() })


                    }
                )
            }
        }
    }

    private fun saveUserInfo(uid:String) {

        viewModelScope.launch(Dispatchers.IO) {
            _userInformation["userUid"] = uid
            _userInformation["providerId"] = "email"
            _userInformation["dateCreated"] = Date().toString()
            _userInformation["email"] = _emailFormState.value.text
            _userInformation["firstName"] = _firstNameFormState.value.text
            _userInformation["lastName"] = _lastNameFormState.value.text
            val profile = UserProfile(
                uid = uid ,
                email = _emailFormState.value.text ,
                providerId = "EMAIL" ,
                firstName = _firstNameFormState.value.text ,
                lastName = _lastNameFormState.value.text ,
                isEmailVerified = false ,
            )
           val result =  userRepo.addProfile(userProfile = profile)
            if(result.isFailure){
                Log.d("Test" , (result.exceptionOrNull()?:Exception("Unknown Error")).stackTraceToString())
            }

        }
    }
    private suspend fun navigateToNextScreen(email:String = _userInformation["email"]?:"DEFAULT_EMAIL"){

            navigateToNextScreen?.let {

                it.invoke(
                    email
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
        _firstNameFormState.update {
            it.copy(
                errorStringResource = null,
                state = UIState.Loading
            )
        }
        _lastNameFormState.update {
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

    fun onEmailChanged(string : String) {
        _emailFormState.update { it.copy(text = string) }
    }

    fun onPasswordChanged(string : String) {
        _passwordFormState.update { it.copy(text = string) }
        _passwordProperties.update { ValidatePassword().analyse(_passwordFormState.value.text) }
    }

    fun onTermsChanged(boolean : Boolean) {
        //_termsIsChecked.update { boolean }
        _termsState.update { if(boolean) UIState.Completed else UIState.Enabled }
    }

    fun validatePassword() : Boolean {
        val requiredProperties = listOf(
            PasswordProperties.ContainsUppercase,
            PasswordProperties.ContainsDigits,
            PasswordProperties.IsLongEnough
        )
        var isValid = true
        requiredProperties.onEach {
            if (it !in _passwordProperties.value) isValid = false
        }
        return isValid
    }

    private fun checkInput() : Boolean {
        if (_termsState.value.isEnabled()) {
            onError(AuthManager.Errors.TermsNotChecked)
            return false
        }

        if(_firstNameFormState.value.text.isBlank()){
            _firstNameFormState.update { it.copy(errorStringResource = R.string.error_empty_field) }
            return false
        }

        if(_lastNameFormState.value.text.isBlank()){
            _lastNameFormState.update { it.copy(errorStringResource = R.string.error_empty_field) }
            return false
        }

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

        val passwordIsValid = validatePassword()

        if (!passwordIsValid) {
            onError(AuthManager.Errors.PasswordTooWeak)
        }
        return passwordIsValid && emailState == EmailProperties.Valid
    }

   private fun cacheExerciseDatabase(onComplete :suspend (String) -> Unit) {
       viewModelScope.launch(Dispatchers.IO) {
           try {
               //retrieve latest database version from cloud
               val result = repository.onUpdateLocalDatabase()

               if (result.isSuccess) {
                   withContext(Dispatchers.Main){
                       onComplete(getUserUid())
                   }

               } else {
                   (result.exceptionOrNull() ?: Exception("Unknown Exception")).printStackTrace()
               }
           } catch (e : Exception) {
               e.printStackTrace()
           }
       }
   }
   

    fun getUserEmail() : String {
        return _userInformation["email"]?:"DEFAULT_EMAIL"
    }

    fun onFirstNameChanged(string : String ) {
        _firstNameFormState.update { it.copy(text = string) }
    }

    fun onLastNameChanged(string : String) {
        _lastNameFormState.update { it.copy(text = string) }
    }


}