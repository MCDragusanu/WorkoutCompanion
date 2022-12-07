package com.example.workoutcompanion.auth

import android.util.Log
import com.example.workoutcompanion.auth.AuthService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthServiceImpl: AuthService {
    override suspend fun logUserWithEmail(
        email: String,
        password: String,
        callback: AuthService.EventCallbacks
    ) {
        Log.d("AuthService", "Login Service Started")
        try {

            callback.onStart()
            val request = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            request.await()
            if (request.isSuccessful) callback.onSuccess()
            else callback.onError(request.exception!!)
        } catch (e:Exception){
            callback.onError(e)
        }

    }

    override suspend fun sendForgotPasswordEmail(
        email: String,
        callback: AuthService.EventCallbacks
    ) {
        try {
            callback.onStart()
            val request = FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            request.await()
            if (request.isSuccessful) callback.onSuccess()
            else callback.onError(request.exception!!)
        } catch (e: Exception) {
            callback.onError(e)
        }
    }

    override suspend fun createAccountWithEmailAndPassword(
        email: String,
        password: String,
        callback: AuthService.EventCallbacks
    ) {
        try {
            callback.onStart()
            val request = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            request.await()
            if (request.isSuccessful) callback.onSuccess(request.result.user!!.uid)
            else callback.onError(request.exception!!)
        }catch (e:Exception){
            callback.onError(e)
        }
    }
}