package com.example.workoutcompanion.common

import com.example.workoutcompanion.screens.entry.create_account_account.PasswordState

object PasswordChecker {


    fun checkPassword(password:String):PasswordState = PasswordState(isShort = password.length<10 , noDigits = password.count { it.isDigit() } == 0)
}