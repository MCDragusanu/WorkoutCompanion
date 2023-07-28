package com.example.workoutcompanion.core.data.user_database.common

import kotlinx.coroutines.CoroutineScope

interface ProfileDataSource {

   suspend  fun getProfileFromCloudSource(uid:String , scope : CoroutineScope): Result<UserProfile?>



}