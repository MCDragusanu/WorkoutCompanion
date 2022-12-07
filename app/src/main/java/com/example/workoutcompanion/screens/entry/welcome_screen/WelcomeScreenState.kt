package com.example.workoutcompanion.screens.entry.welcome_screen

data class WelcomeScreenState(val isLoading:Boolean ,
                              val txtEmail:String,
                              val txtPassword:String,
                              val loginCompleted:Boolean,
                              val error: Error? = null){
    sealed class Error(var message:String){
        object UserNotFound: Error("There is no account linked to that email")
        object PasswordInvalid: Error("Password is invalid")
        object EmptyField:Error("Please complete this field")
        object UnknownError: Error("Unknown Error has occurred while logging in")
        object InvalidEmailAddress: Error("Please enter a valid email address")
    }
}
