package com.example.workoutcompanion.auth

import android.util.Log
import com.example.workoutcompanion.auth.AuthService
import com.google.firebase.auth.FirebaseAuth

class AuthServiceImpl: AuthService {
    override fun logUserWithEmail(email: String, password: String, callback: AuthService.EventCallbacks) {
        Log.d("AuthService", "Login Service Started")
        callback.onStart()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                Log.d("AuthService", "Login Service finished with Success!")
                callback.onSuccess()
            }else {
                if(it.exception!=null){
                    Log.d("AuthService", "Login Service finished with Success!")
                    callback.onError(exception = it.exception!!)
                }
            }
        }
    }
}