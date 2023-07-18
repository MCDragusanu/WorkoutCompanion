package com.example.workoutcompanion.login.auth_service

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthManagerTestImpl : AuthManager{
    override suspend fun loginUser(
        email: String,
        password: String,
        onLoading: () -> Unit,
        onSuccess: (String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        try {

            if (email.isEmpty()) throw AuthManager.Errors.NoEmailProvided
            if (password.isEmpty()) throw AuthManager.Errors.NoPasswordProvided

            onLoading()
            delay(1500)
            onSuccess("TEST_USER_UID")
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                translateError(e, onError)
            }
        }
    }

    override suspend fun registerUser(
        email: String,
        password: String,
        onLoading: () -> Unit,
        onSuccess: (String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        try {
            if (email.isEmpty()) throw AuthManager.Errors.NoEmailProvided
            if (password.isEmpty()) throw AuthManager.Errors.NoPasswordProvided
            onLoading()
           delay(1500)
            onSuccess("TEST_USER_UID")
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                translateError(e, onError)
            }
        }
    }

    override suspend fun deleteUser(
        email: String,
        password: String,
        onLoading: () -> Unit,
        onSuccess: (String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        try {
            if (email.isEmpty()) throw AuthManager.Errors.NoEmailProvided
            if (password.isEmpty()) throw AuthManager.Errors.NoPasswordProvided
            onLoading()
            delay(1500)
            onSuccess("TEST_USER_UID")
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                translateError(e, onError)
            }
        }
    }

    override suspend fun changeEmail(
        email: String,
        password: String,
        newEmail: String,
        onLoading: () -> Unit,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        try {
            if (email.isEmpty()) throw AuthManager.Errors.NoEmailProvided
            if (password.isEmpty()) throw AuthManager.Errors.NoPasswordProvided
            onLoading()
           delay(1500)
            onSuccess()
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                translateError(e, onError)
            }
        }
    }

    override suspend fun changePassword(
        email: String,
        password: String,
        newPassword: String,
        onLoading: () -> Unit,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        try {
            if (email.isEmpty()) throw AuthManager.Errors.NoEmailProvided
            if (password.isEmpty()) throw AuthManager.Errors.NoPasswordProvided
            onLoading()
            delay(1500)
            onSuccess()
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                translateError(e, onError)
            }
        }
    }

    override suspend fun sendEmailVerification(
        email: String,
        onLoading: () -> Unit,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        try {
            if (email.isEmpty()) throw AuthManager.Errors.NoEmailProvided
            onLoading()
            delay(1500)
            onSuccess()
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                translateError(e, onError)
            }
        }
    }

    override suspend fun sendResetPassword(
        email: String,
        onLoading: () -> Unit,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        try {

            if (email.isEmpty()) throw AuthManager.Errors.NoEmailProvided
            onLoading()
            delay(1500)
            onSuccess()

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                translateError(e, onError)
            }
        }
    }

   private fun translateError(exception: Exception, callback: (Exception) -> Unit) {
        val translatedError = when (exception) {

            is AuthManager.Errors.InvalidEmail->AuthManager.Errors.InvalidEmail
            is AuthManager.Errors.NoEmailProvided->AuthManager.Errors.NoEmailProvided
            is AuthManager.Errors.NoPasswordProvided->AuthManager.Errors.NoPasswordProvided
            is FirebaseNetworkException ->AuthManager.Errors.NoInternetConnection
            is FirebaseAuthUserCollisionException ->AuthManager.Errors.AccountCollision
            is FirebaseAuthEmailException -> AuthManager.Errors.InvalidEmail
            is FirebaseAuthInvalidUserException -> AuthManager.Errors.NoAccountFound
            is FirebaseAuthInvalidCredentialsException -> AuthManager.Errors.InvalidCredentials
            else -> exception
        }
        callback(translatedError)
    }
}