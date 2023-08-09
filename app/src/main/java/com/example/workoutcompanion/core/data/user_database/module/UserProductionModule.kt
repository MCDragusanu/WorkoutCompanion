package com.example.workoutcompanion.core.data.user_database.module

import android.app.Application
import com.example.workoutcompanion.core.data.auth_service.AuthModule
import com.example.workoutcompanion.core.data.di.ComponentType
import com.example.workoutcompanion.core.data.user_database.cloud.CloudProfileRepositoryImpl
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepository
import com.example.workoutcompanion.core.data.user_database.common.ProfileRepositoryImpl
import com.example.workoutcompanion.core.data.user_database.local.LocalProfileRepositoryImpl
import com.example.workoutcompanion.core.data.user_database.local.ProductionUserDatabase
import com.example.workoutcompanion.core.data.user_database.cloud.CloudProfileRepository
import com.example.workoutcompanion.core.data.user_database.local.LocalProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UserProductionModule {

    @Provides
    @ComponentType(testing = false)
    fun provideUserDatabase(application : Application) : ProductionUserDatabase {
        //Log.d("Test" , "Provied production User Database")
        return ProductionUserDatabase.getInstance(application)
    }

    @Provides
    @ComponentType(testing = false)
    fun provideCloudRepository() : CloudProfileRepository {
        //Log.d("Test" , "Provided production Cloud Profile Repository")
        return CloudProfileRepositoryImpl()
    }

    @Provides
    @ComponentType(testing = false)
    fun provideLocalRepository(application : Application) : LocalProfileRepository {
        provideUserDatabase(application).apply {
            //Log.d("Test" , "Provided production Local Profile Repository")
            return LocalProfileRepositoryImpl(this.dao)
        }
    }

    @Provides
    @ComponentType(testing = false)
    fun provideProfileRepository(application : Application) : ProfileRepository {
        //Log.d("Test" , "Provided production Profile Repository")
        return ProfileRepositoryImpl(
            this.provideCloudRepository() ,
            this.provideLocalRepository(application) ,
            AuthModule.provideProductionAuthService()
        )
    }
}