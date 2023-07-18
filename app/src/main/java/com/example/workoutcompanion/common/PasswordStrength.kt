package com.example.workoutcompanion.common

class PasswordStrength(
    val isLongEnough: Boolean,
    val containsDigits: Boolean,
    val containsUpperCase: Boolean
) {
    fun isStrong(): Boolean = isLongEnough && containsDigits && containsUpperCase

    fun isMediocre(): Boolean =
        (isLongEnough && containsDigits && !containsUpperCase) ||
                (isLongEnough && containsUpperCase && !containsDigits) ||
                (isLongEnough && !containsDigits && !containsUpperCase)

    fun isWeak(): Boolean = !isLongEnough && !containsDigits && !containsUpperCase
}