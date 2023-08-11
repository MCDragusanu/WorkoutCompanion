package com.example.workoutcompanion.core.data.user_database.module

import android.app.Application
import android.util.Log
import com.example.workoutcompanion.core.data.auth_service.AuthModule
import com.example.workoutcompanion.core.data.di.Testing

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
    @Testing
    fun provideTestUserDatabase(application : Application) : TestUserDatabase {
        Log.d("Test" , "Provided testing User Database")
        return TestUserDatabase.getInstance(application)
    }

    @Provides
    @Testing
    fun provideTestCloudRepository() : CloudProfileRepository {
        Log.d("Test" , "Provided test Cloud Profile Repository")
        return CloudProfileRepositoryTestImpl()
    }

    @Provides
    @Testing
    fun provideTestLocalRepository() : LocalProfileRepositoryTestImpl {
        Log.d("Test" , "Provided test Local Profile Repository")

            return LocalProfileRepositoryTestImpl()
    }

    @Provides
    @Testing
    fun provideProfileRepository() : ProfileRepository {
        Log.d("Test" , "Provided test Profile Repository")
        return ProfileRepositoryImpl(
            this.provideTestCloudRepository() ,
            this.provideTestLocalRepository() ,
            AuthModule.provideTestAuthService()
        )

    }
}