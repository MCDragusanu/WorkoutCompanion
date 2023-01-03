package com.example.workoutcompanion.presentation.graphs.entry.screens.welcome

import com.example.workoutcompanion.presentation.ui_state.UIState

data class WelcomeScreenState(val isLoading : Boolean = false,
                              val txtEmail: String ="",
                              val txtPassword:String ="",
                              val btnLoginState : UIState = UIState.Default,
                              val etEmailState: UIState = UIState.Default,
                              val etPasswordState: UIState = UIState.Default,
                              val errorEmail : Error?=null,
                              val errorPassword : Error?=null,
                              val unknownError: Error?=null
                         ) {


    sealed class Error(var message: String) {
        object EmailInvalid : Error("Please enter a valid email!")
        object EmptyEmail : Error("Please enter an email!")
        object EmptyPassword : Error("Please enter a password!")
        object ShortPassword : Error("Password is too short!")
        object PasswordInvalid : Error("Password is wrong!")
        object NoUserFound : Error("No account was found!")
        object NoInternetConnection : Error("Please check your internet connection and try again!")
        object UnknownError : Error("An unknown error has occurred :(")
    }
}