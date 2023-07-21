package com.example.workoutcompanion.entry_navigation.login.auth_service

import com.google.firebase.auth.FirebaseAuth

interface AuthManager {
    suspend fun loginUser(
        email: String,
        password: String,
        onLoading:  () -> Unit,
        onSuccess:  (String) -> Unit,
        onError:  (Exception) -> Unit
    )

   suspend  fun registerUser(
        email: String,
        password: String,
        onLoading:  () -> Unit,
        onSuccess:  (String) -> Unit,
        onError:  (Exception) -> Unit
    )

   suspend  fun deleteUser(
        email: String,
        password: String,
        onLoading:  () -> Unit,
        onSuccess:  (String) -> Unit,
        onError:  (Exception) -> Unit
    )

   suspend  fun changeEmail(
        email: String,
        password: String,
        newEmail: String,
        onLoading:  () -> Unit,
        onSuccess:  () -> Unit,
        onError:  (Exception) -> Unit
    )

    suspend fun changePassword(
        email: String,
        password: String,
        newPassword: String,
        onLoading:  () -> Unit,
        onSuccess:  () -> Unit,
        onError:  (Exception) -> Unit
    )

   suspend  fun sendEmailVerification(
        email: String,
        onLoading:  () -> Unit,
        onSuccess:  () -> Unit,
        onError:  (Exception) -> Unit
    )
    suspend fun sendResetPassword(
        email: String,
        onLoading:  () -> Unit,
        onSuccess:  () -> Unit,
        onError:  (Exception) -> Unit
    )

   suspend fun isLoggedIn():Boolean{
        return FirebaseAuth.getInstance().currentUser!=null
    }

   suspend fun signOut(){
        FirebaseAuth.getInstance().signOut()
    }

    sealed class Errors(message:String):Exception(message){

        object NoEmailProvided: Errors("No email provided")//
        object NoPasswordProvided: Errors("No password provided")//
        object NoAccountFound: Errors("There is no account linked to that email")//
        object InvalidCredentials: Errors("The credentials you entered are invalid")//
        object AccountCollision: Errors("The credentials are already linked to another account")
        object PasswordTooWeak: Errors("Password is too weak")
        object InvalidEmail: Errors("The email you is invalid")//
        object TermsNotChecked: Errors("You must agree with the policies before continuing")
        object NoInternetConnection: Errors("You must have an active network connection in order to continue")

    }
}