package com.example.workoutcompanion.core.data.di

import dagger.hilt.android.HiltAndroidApp
import javax.inject.Qualifier

@HiltAndroidApp
class Application:android.app.Application()

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Production

@Target(
    AnnotationTarget.PROPERTY ,
    AnnotationTarget.VALUE_PARAMETER ,
    AnnotationTarget.FUNCTION ,
    AnnotationTarget.EXPRESSION
)
@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class Testing(){

}