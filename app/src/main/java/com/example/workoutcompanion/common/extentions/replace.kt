package com.example.workoutcompanion.common.extentions

import android.util.Log

fun <T> List<T>.replace(newValue: T , block: (T) -> Boolean): List<T> {
  return map {
    if (block(it)) {
      Log.d("Test" , "Value found")
      newValue
    } else {
      Log.d("Test" , "Value not found")
      it
    }
  }
}
