package com.example.workoutcompanion.common.use_cases.password

import com.example.workoutcompanion.R

enum class PasswordProperties(val weight :Double , val condition:(String)->Boolean , val stringResourceId:Int) {
    ContainsLetters(0.22 , {it.any { it.isLetter() }} , R.string.password_properties_contains_letters),
    IsLongEnough(0.22, { string -> string.length > 10 } , R.string.password_properties_is_long_enough),
    ContainsDigits(0.22, { string -> string.any { it.isDigit() } } , R.string.password_properties_contains_digits),
    ContainsUppercase(0.22, { string -> string.any { it.isUpperCase() } } , R.string.password_properties_contains_upper_case),
    ContainsSpecialCharacters(0.12, { string -> string.any { !it.isLetterOrDigit() } } , R.string.password_properties_contains_special_characters),
}