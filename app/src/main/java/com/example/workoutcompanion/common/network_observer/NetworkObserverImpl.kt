package com.example.workoutcompanion.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NetworkObserverImpl(private val context: Context): NetworkObserver {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<NetworkObserver.NetworkStatus> {
        return callbackFlow<NetworkObserver.NetworkStatus> {
            val callback = object : ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    Log.d("Test" , "onAvailable::"+NetworkObserver.NetworkStatus.Available.name)
                   trySend( NetworkObserver.NetworkStatus.Available)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    Log.d("Test" , "onLost::"+NetworkObserver.NetworkStatus.Lost.name)
                    trySend( NetworkObserver.NetworkStatus.Lost)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    Log.d("Test" , "onUnavailable::"+NetworkObserver.NetworkStatus.Unavailable.name)
                    trySend( NetworkObserver.NetworkStatus.Unavailable)
                }
            }
            val request = NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            connectivityManager.registerNetworkCallback(request , callback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }
    }
}