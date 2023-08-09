package com.example.workoutcompanion.core.domain.use_cases

import android.util.Log
import com.example.workoutcompanion.core.presentation.app_state.Language

class TranslateLanguageCode {
    fun execute(code:String): Language {
        Log.d("Test" , "Language code = $code")
        return Language.English
    }
}