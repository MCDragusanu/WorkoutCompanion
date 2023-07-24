package com.example.workoutcompanion.core.data.user_database.module

import android.app.Application
import android.util.Log
import com.example.workoutcompanion.core.data.auth_service.AuthModule
import com.example.workoutcompanion.core.data.user_database.cloud.TestCloudProfileRepository
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepositoryImpl
import com.example.workoutcompanion.core.data.user_database.local.TestLocalRepository
import com.example.workoutcompanion.core.data.user_database.local.TestUserDatabase
import com.example.workoutcompanion.core.data.di.Testing
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)

object TestingModule {

    @Provides
    @Testing
    fun provideTestUserDatabase(application: Application): TestUserDatabase {
        Log.d("Test" , "Provided testing User Database")
        return TestUserDatabase.getInstance(application)
    }

    @Provides
    @Testing
    fun provideTestCloudRepository(): ProfileRepository {
        Log.d("Test" , "Provided test Cloud Profile Repository")
        return TestCloudProfileRepository()
    }

    @Provides
    @Testing
    fun provideTestLocalRepository(application : Application): ProfileRepository {
        Log.d("Test" , "Provided test Local Profile Repository")
        provideTestUserDatabase(application).apply {
            return TestLocalRepository(this.dao)
        }
    }

    @Provides
    @Testing
    fun provideProfileRepository(application : Application): ProfileRepositoryImpl {
        Log.d("Test" , "Provided test Profile Repository")
        return ProfileRepositoryImpl(provideTestCloudRepository() , provideTestLocalRepository(application) , AuthModule.provideTestAuthService())

    }
}