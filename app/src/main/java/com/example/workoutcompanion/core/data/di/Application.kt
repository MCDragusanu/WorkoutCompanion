package com.example.workoutcompanion.core.data.di

import dagger.hilt.android.HiltAndroidApp
import javax.inject.Qualifier

@HiltAndroidApp
class Application:android.app.Application()




@Qualifier
annotation class Testing()
@Qualifier annotation class Production()

