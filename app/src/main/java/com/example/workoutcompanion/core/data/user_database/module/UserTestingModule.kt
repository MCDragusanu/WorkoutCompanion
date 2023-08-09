package com.example.workoutcompanion.core.data.user_database.module

import android.app.Application
import android.util.Log
import com.example.workoutcompanion.core.data.auth_service.AuthModule
import com.example.workoutcompanion.core.data.di.ComponentType
import com.example.workoutcompanion.core.data.user_database.cloud.CloudProfileRepositoryTestImpl
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepositoryImpl
import com.example.workoutcompanion.core.data.user_database.local.LocalProfileRepositoryTestImpl
import com.example.workoutcompanion.core.data.user_database.local.TestUserDatabase
import com.example.workoutcompanion.core.data.user_database.cloud.CloudProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)

object UserTestingModule {

    @Provides
    @ComponentType(true)
    fun provideTestUserDatabase(application : Application) : TestUserDatabase {
        Log.d("Test" , "Provided testing User Database")
        return TestUserDatabase.getInstance(application)
    }

    @Provides
    @ComponentType(true)
    fun provideTestCloudRepository() : CloudProfileRepository {
        Log.d("Test" , "Provided test Cloud Profile Repository")
        return CloudProfileRepositoryTestImpl()
    }

    @Provides
    @ComponentType(true)
    fun provideTestLocalRepository(application : Application) : LocalProfileRepositoryTestImpl {
        Log.d("Test" , "Provided test Local Profile Repository")
        provideTestUserDatabase(application).apply {
            return LocalProfileRepositoryTestImpl(this.dao)
        }
    }

    @Provides
    @ComponentType(testing = true)
    fun provideProfileRepository(application : Application) : ProfileRepository {
        Log.d("Test" , "Provided test Profile Repository")
        return ProfileRepositoryImpl(
            this.provideTestCloudRepository() ,
            this.provideTestLocalRepository(application) ,
            AuthModule.provideTestAuthService()
        )

    }
}