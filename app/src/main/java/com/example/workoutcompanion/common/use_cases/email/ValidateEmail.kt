package com.example.workoutcompanion.common.use_cases.email

import android.util.Patterns

class ValidateEmail {
    fun execute(email: String): EmailProperties {
        if (email.isBlank()) return EmailProperties.Empty
        return if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) EmailProperties.Valid
        else EmailProperties.Invalid
    }
}