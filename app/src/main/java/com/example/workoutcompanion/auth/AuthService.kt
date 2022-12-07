package com.example.workoutcompanion.auth

interface AuthService {
    enum class LoginEvent{
        Default,
        Started,
        Success,
        UserNotFound,
        IncorrectPassword,
        UnknownError;
    }
    fun logUserWithEmail(email:String , password:String , callback: EventCallbacks)
    fun checkEmailIsValid(email: String) = if (email.isEmpty()) {
         false;
    } else {
         android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    interface EventCallbacks{
        fun onStart()
        fun onSuccess()
        fun onError(exception: Exception)
    }
}