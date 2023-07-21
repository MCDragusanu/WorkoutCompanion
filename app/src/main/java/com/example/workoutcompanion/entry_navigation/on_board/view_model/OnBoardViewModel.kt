package com.example.workoutcompanion.entry_navigation.on_board.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.R
import com.example.workoutcompanion.common.FormState
import com.example.workoutcompanion.common.NetworkObserver
import com.example.workoutcompanion.common.UIState
import com.example.workoutcompanion.common.use_cases.email.EmailProperties
import com.example.workoutcompanion.common.use_cases.email.ValidateEmail
import com.example.workoutcompanion.common.use_cases.password.PasswordProperties
import com.example.workoutcompanion.common.use_cases.password.ValidatePassword
import com.example.workoutcompanion.exercise_database.module.CloudDatabaseModule
import com.example.workoutcompanion.exercise_database.module.LocalDatabaseModule
import com.example.workoutcompanion.exercise_database.repo.ExerciseDataSource
import com.example.workoutcompanion.exercise_database.repo.ExerciseRepository
import com.example.workoutcompanion.exercise_database.use_cases.HandleDatabaseUpdates
import com.example.workoutcompanion.exercise_database.version_control.repo.DatabaseVersionDataSource
import com.example.workoutcompanion.exercise_database.version_control.repo.DatabaseVersionRepository
import com.example.workoutcompanion.entry_navigation.login.auth_service.AuthManager
import com.example.workoutcompanion.entry_navigation.login.module.AuthModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.Date
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    private val versionRepository : DatabaseVersionRepository ,

    @Named(CloudDatabaseModule.cloudDatabaseVersionDataSource)
    private val cloudVersionDataSource : DatabaseVersionDataSource ,

    private val exerciseRepository : ExerciseRepository ,

    @Named(CloudDatabaseModule.cloudExerciseDataSource)
    private val cloudExerciseDataSource : ExerciseDataSource ,

    @Named(LocalDatabaseModule.localDatabaseVersionDataSource)
    private val localVersionDataSource : DatabaseVersionDataSource ,

    @Named(AuthModule.testImpl) private val authManager : AuthManager ,

    private val networkObserver : NetworkObserver) : ViewModel() {

    private val _passwordProperties = MutableStateFlow<List<PasswordProperties>>(emptyList())
    val passwordProperties = _passwordProperties.asStateFlow()

    private val _emailFormState = MutableStateFlow(FormState())
    val emailFormState = _emailFormState.asStateFlow()

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

                        this.cancel()
                    }
                )
            }
        }
    }

    private fun saveUserInfo(uid:String){
        _userInformation["userUid"] = uid
        _userInformation["providerId"] = "email"
        _userInformation["dateCreated"] = Date().toString()
        _userInformation["email"] = _emailFormState.value.text
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
        _ctaState.update {
            UIState.Loading
        }
    }

    private fun putUiInEnabledState() {
        viewModelScope.launch(Dispatchers.Main){
            _emailFormState.update {
                it.copy(
                    errorStringResource = null ,
                    state = UIState.Enabled
                )
            }
            _errorFlow.emit(null)
            _termsState.update { UIState.Enabled }
            _passwordFormState.update {
                it.copy(
                    errorStringResource = null ,
                    state = UIState.Enabled
                )
            }
            _ctaState.update {
                UIState.Enabled
            }
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
            try{
                //retrieve latest database version from cloud
               HandleDatabaseUpdates().execute(
                   exerciseRepository = exerciseRepository,
                   versionRepository = versionRepository,
                   localVersionDataSource = localVersionDataSource,
                   cloudExerciseDataSource = cloudExerciseDataSource,
                   cloudVersionDataSource = cloudVersionDataSource,
                   onError = {
                       it.printStackTrace()
                   },
                   onSuccess = {
                       viewModelScope.launch(Dispatchers.Main){
                           onComplete(getUserUid())
                       }
                   }
               )
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
   

    fun getUserEmail() : String {
        return _userInformation["email"]?:"DEFAULT_EMAIL"
    }
}