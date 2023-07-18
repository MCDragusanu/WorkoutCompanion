package com.example.workoutcompanion.common.use_cases.password

class ValidatePassword {

    fun analyse(string: String):List<PasswordProperties>{
        return PasswordProperties.values().filter { it.condition(string) }
    }
}