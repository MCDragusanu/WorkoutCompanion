package com.example.workoutcompanion.presentation

import android.util.Patterns

class WelcomeScreenState(val isLoading : Boolean ,
                         var txtEmail: String,
                         var txtPassword:String,
                         var isLoggedIn:Boolean,
                         var btnLoginState : CallToActionEvent = CallToActionEvent.Neutral,
                         var error :Error?=null
                         ) {
    fun emailFormatIsValid(): Boolean {
        var isValid = false
        if (this.txtEmail.isEmpty()) {
            error = Error.EmptyEmail
        } else {
            isValid = Patterns.EMAIL_ADDRESS.matcher(this.txtEmail).matches()
            if (!isValid) error = Error.EmailInvalid
        }
        if (!isValid) btnLoginState = CallToActionEvent.Error
        return isValid
    }

    fun passwordFormatIsValid() :Boolean{
        var isValid = false
        if (this.txtPassword.isEmpty()) {
            error = Error.EmptyPassword
        } else {
            isValid = txtPassword.length>8
            if (!isValid) error = Error.ShortPassword
        }
        if (!isValid) btnLoginState = CallToActionEvent.Error
        return isValid
    }
    fun formIsValid() = passwordFormatIsValid()&&emailFormatIsValid()

    sealed class Error(val message: String) {
        object EmailInvalid : Error("Please enter a valid email!")
        object EmptyEmail : Error("Please enter an email!")
        object EmptyPassword : Error("Please enter a password!")
        object ShortPassword : Error("Please enter a password!")
        object PasswordInvalid : Error("Password is wrong!")
        object NoUserFound : Error("No account was found!")
        object NoInternetConnection : Error("Please check your internet connection and try again!")
        object UnknownError : Error("An unknown error has occurred :(")
    }
}