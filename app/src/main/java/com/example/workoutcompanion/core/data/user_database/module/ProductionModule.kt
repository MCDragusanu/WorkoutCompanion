package com.example.workoutcompanion.core.data.user_database.module

import android.app.Application
import android.util.Log
import com.example.workoutcompanion.core.data.user_database.cloud.CloudProfileRepository
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepositoryImpl
import com.example.workoutcompanion.core.data.user_database.local.LocalProfileRepository
import com.example.workoutcompanion.core.data.user_database.local.ProductionUserDatabase
import com.example.workoutcompanion.core.data.di.Production
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object ProductionModule {

    @Provides
    @Production
    fun provideUserDatabase(application:Application): ProductionUserDatabase {
        //Log.d("Test" , "Provied production User Database")
        return ProductionUserDatabase.getInstance(application)
    }

    @Provides
    @Production
    fun provideCloudRepository():ProfileRepository {
        //Log.d("Test" , "Provided production Cloud Profile Repository")
        return CloudProfileRepository()
    }

    @Provides
    @Production
    fun provideLocalRepository(application : Application):ProfileRepository {
        provideUserDatabase(application).apply {
            //Log.d("Test" , "Provided production Local Profile Repository")
            return LocalProfileRepository(this.dao)
        }
    }

    @Provides
    @Production
    fun provideProfileRepository(application : Application):ProfileRepositoryImpl {
        //Log.d("Test" , "Provided production Profile Repository")
        return ProfileRepositoryImpl(provideCloudRepository() , provideLocalRepository(application))

    }
}