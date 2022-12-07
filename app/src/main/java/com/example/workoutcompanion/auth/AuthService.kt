package com.example.workoutcompanion.auth

interface AuthService {

    suspend fun logUserWithEmail(email: String, password: String, callback: EventCallbacks)
    suspend fun sendForgotPasswordEmail(email: String, callback: EventCallbacks)
    suspend fun createAccountWithEmailAndPassword(email: String, password: String , callback: EventCallbacks)
    fun checkEmailIsValid(email: String) = if (email.isEmpty()) {
        false;
    } else {
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    interface EventCallbacks {
        suspend fun onStart()
        suspend fun onSuccess(firebaseId:String = "")
        suspend fun onError(exception: Exception)
    }
}