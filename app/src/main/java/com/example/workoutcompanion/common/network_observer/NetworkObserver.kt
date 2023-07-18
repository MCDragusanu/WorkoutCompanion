package com.example.workoutcompanion.common

import kotlinx.coroutines.flow.Flow

interface NetworkObserver {

    enum class NetworkStatus {
        Available,
        Unavailable,
        Loosing,
        Lost;
        fun isOnline() = this == Available
        fun isOffline() =!isOnline()
    }


    fun observe(): Flow<NetworkStatus>

}