package com.example.workoutcompanion.core.data.auth_service

import com.example.workoutcompanion.common.NetworkObserver
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthManagerImpl: AuthManager {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
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
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                onSuccess(it.user!!.uid)
            }.addOnFailureListener {
                translateError(it, onError)
            }.await()
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
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                onSuccess(it.user!!.uid)
            }.addOnFailureListener {
                translateError(it, onError)
            }.await()
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
            val credentials = EmailAuthProvider.getCredential(email, password)
            auth.currentUser?.let { user ->
                user.reauthenticate(credentials).addOnFailureListener {
                    translateError(it, onError)
                }.addOnSuccessListener {
                    user.delete()
                    onSuccess(user.uid)
                }
            }
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
            val credentials = EmailAuthProvider.getCredential(email, password)
            auth.currentUser?.let { user ->
                user.reauthenticate(credentials)
                    .addOnFailureListener { translateError(it, onError) }.addOnSuccessListener {
                        user.updateEmail(newEmail)
                            .addOnFailureListener { translateError(it, onError) }
                            .addOnSuccessListener { onSuccess() }
                    }
            }
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
            val credentials = EmailAuthProvider.getCredential(email, password)
            auth.currentUser?.let { user ->
                user.reauthenticate(credentials)
                    .addOnFailureListener { translateError(it, onError) }.addOnSuccessListener {
                        user.updatePassword(newPassword)
                            .addOnFailureListener { translateError(it, onError) }
                            .addOnSuccessListener { onSuccess() }
                    }
            }
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
            auth.currentUser?.let { user ->
                user.sendEmailVerification().addOnFailureListener { translateError(it, onError) }
                    .addOnSuccessListener {
                        onSuccess()
                    }
            }
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
            onLoading()
            if (email.isEmpty()) throw AuthManager.Errors.NoEmailProvided
            auth.sendPasswordResetEmail(email).addOnFailureListener { translateError(it, onError) }
                .addOnSuccessListener {
                    onSuccess()
                }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                translateError(e, onError)
            }
        }
    }

    fun translateError(exception: Exception, callback: (Exception) -> Unit) {
        val translatedError = when (exception) {

            is AuthManager.Errors.InvalidEmail -> AuthManager.Errors.InvalidEmail
            is AuthManager.Errors.NoEmailProvided -> AuthManager.Errors.NoEmailProvided
            is AuthManager.Errors.NoPasswordProvided -> AuthManager.Errors.NoPasswordProvided
            is FirebaseNetworkException-> AuthManager.Errors.NoInternetConnection
            is FirebaseAuthUserCollisionException-> AuthManager.Errors.AccountCollision
            is FirebaseAuthEmailException -> AuthManager.Errors.InvalidEmail
            is FirebaseAuthInvalidUserException -> AuthManager.Errors.NoAccountFound
            is FirebaseAuthInvalidCredentialsException -> AuthManager.Errors.InvalidCredentials
            else -> exception
        }
        callback(translatedError)
    }
}