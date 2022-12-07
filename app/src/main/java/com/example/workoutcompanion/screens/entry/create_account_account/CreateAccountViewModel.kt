package com.example.workoutcompanion.screens.entry.create_account_account

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutcompanion.graphs.OnBoardState
import com.example.workoutcompanion.common.Date
import com.example.workoutcompanion.common.UIState
import com.example.workoutcompanion.auth.AuthService
import com.example.workoutcompanion.auth.AuthServiceImpl
import com.example.workoutcompanion.common.ExactMoment
import com.example.workoutcompanion.room.AccountInformation
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class CreateAccountViewModel:ViewModel() {

    private val provider = AuthServiceImpl()

    private var exactMoment = ExactMoment.fromDate()
    private var firebaseId = "DEFAULT_USER"


    private val _txtEmail = mutableStateOf("")
    var txtEmail: State<String> = _txtEmail

    private val _txtPassword = mutableStateOf("")
    var txtPassword: State<String> = _txtPassword

    private val _txtFirstName = mutableStateOf("")
    var txtFirstName: State<String> = _txtFirstName

    private val _txtLastName = mutableStateOf("")
    var txtLastName: State<String> = _txtLastName

    private val _dateOfBirth = mutableStateOf(Date.fromLocalDate(LocalDate.now().minusYears(18)))
    var dateOfBirth: State<Date> = _dateOfBirth

    private val _gender = mutableStateOf(0)
    var gender: State<Int> = _gender

    private val _stateEmail = MutableStateFlow(UIState.Enabled)
    var stateEmail = _stateEmail.asStateFlow()

    private val _statePassword = MutableStateFlow(UIState.Enabled)
    var statePassword = _statePassword.asStateFlow()

    private val _stateButton = MutableStateFlow(UIState.Enabled)
    var stateButton = _stateButton.asStateFlow()

    private val _error = MutableStateFlow<Errors?>(null)
    var error = _error.asStateFlow()

    private val _emailError = MutableStateFlow<Errors?>(null)
    var errorEmail = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow<Errors?>(null)
    var errorPassword = _passwordError.asStateFlow()

    fun setEmail(newEmail: String) {
        this._txtEmail.value = newEmail
        txtEmail = _txtEmail
    }

    fun setPassword(newPassword: String) {
        this._txtPassword.value = newPassword
        txtEmail = _txtPassword
    }

    fun setFirstName(newName: String) {
        this._txtFirstName.value = newName
        txtFirstName = _txtFirstName
    }

    fun setLastName(newName: String) {
        this._txtLastName.value = newName
        txtLastName = _txtLastName
    }

    fun setGender(newGender: Int) {
        this._gender.value = newGender
        gender = _gender
    }

    fun createAccount() {
        viewModelScope.launch(IO) {
            if (inputIsValid()) {
                provider.createAccountWithEmailAndPassword(
                    _txtEmail.value,
                    _txtPassword.value,
                    callback = object : AuthService.EventCallbacks {
                        override suspend fun onStart() {
                            _stateButton.emit(UIState.Loading)
                            _emailError.emit(null)
                            _passwordError.emit(null)
                            updateStates()
                        }

                        override suspend fun onSuccess(firebaseId: String) {

                            this@CreateAccountViewModel.exactMoment = ExactMoment.fromDate()
                            this@CreateAccountViewModel.firebaseId = firebaseId
                            _stateEmail.emit(UIState.Completed)
                            _statePassword.emit(UIState.Completed)
                            _stateButton.emit(UIState.Completed)
                            _error.emit(null)
                            Log.d("Test", "Account Created")
                            updateStates()
                        }

                        override suspend fun onError(exception: Exception) {
                            Log.d("Test", exception.message!!)
                            _stateButton.emit(UIState.Error)
                            when {
                                exception is FirebaseAuthUserCollisionException -> {
                                    _stateEmail.emit(UIState.Error)
                                    _statePassword.emit(UIState.Enabled)
                                    _emailError.emit(Errors.EmailAlreadyInse)
                                }
                                exception is FirebaseAuthWeakPasswordException -> {
                                    _passwordError.emit(Errors.PasswordTooWeak)
                                    _statePassword.emit(UIState.Error)
                                    _stateEmail.emit(UIState.Enabled)
                                }
                                !provider.checkEmailIsValid(_txtEmail.value) -> {
                                    _stateEmail.emit(UIState.Error)
                                    _statePassword.emit(UIState.Enabled)
                                    _emailError.emit(Errors.InvalidEmail)
                                }
                                else -> {
                                    _stateEmail.emit(UIState.Enabled)
                                    _statePassword.emit(UIState.Enabled)
                                    _error.emit(Errors.UnknownError.apply {
                                        this.error = exception.message!!
                                    })
                                }
                            }
                            updateStates()
                        }
                    })
            }
        }
    }


    fun updateStates() {
        stateEmail = _stateEmail.asStateFlow()
        statePassword = _statePassword.asStateFlow()
        errorEmail = _emailError
        errorPassword = _passwordError

    }

    private suspend fun inputIsValid(): Boolean {
        val newEmail = if (_txtEmail.value.isEmpty()) UIState.Error else UIState.Enabled
        val newPassword = if (_txtPassword.value.isEmpty()) UIState.Error else UIState.Enabled
        val newBtnState =
            if (!newEmail.isError() && !newPassword.isError()) UIState.Enabled else UIState.Error
        _stateEmail.emit(newEmail)
        _statePassword.emit(newPassword)
        if (newEmail.isError()) _emailError.emit(Errors.EmptyEmail)
        if (newPassword.isError()) _passwordError.emit(Errors.EmptyPassword)
        updateStates()
        return !newBtnState.isError()
    }

    fun setDate(it: Date) {
        _dateOfBirth.value = it
        dateOfBirth = _dateOfBirth
    }


    fun getAccountInformation(): OnBoardState =
        OnBoardState(
            accountIsCreated = true, accountInformation = AccountInformation(
                account_uid = firebaseId,
                email = _txtEmail.value,
                accountCreationDate = exactMoment.asString(),
                dateOfBirth = ExactMoment.fromDate(_dateOfBirth.value).asString(),
                firstName = _txtFirstName.value,
                lastEntry = exactMoment.asString(),
                gender = _gender.value,
                lastName = _txtLastName.value,
                rememberMe = true,
                trainingExperience = 1,
                unitOfMeasureHeight = 0,
                unitOfMeasurementWeight = 0
            )
        )

    sealed class Errors(var error: String) {
        object EmptyPassword : Errors("Please complete this field")
        object EmptyEmail : Errors("Please complete this field")
        object InvalidEmail : Errors("Please enter a valid email")
        object PasswordTooWeak : Errors("Please enter a stronger password . Use digits from 0-9")
        object EmailAlreadyInse : Errors("This email is linked to another account")
        object UnknownError : Errors("")
    }
}
