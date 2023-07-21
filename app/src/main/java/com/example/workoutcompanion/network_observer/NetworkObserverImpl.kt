package com.example.workoutcompanion.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NetworkObserverImpl(private val context: Context): NetworkObserver {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override suspend fun monitorConnection(externalScope:CoroutineScope): SharedFlow<Boolean> {
        return callbackFlow<Boolean> {
            val callback = object : ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    Log.d("Test" , "onAvailable::"+NetworkObserver.NetworkStatus.Available.name)
                   trySend( true)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    Log.d("Test" , "onLost::"+NetworkObserver.NetworkStatus.Lost.name)
                    trySend( false)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    Log.d("Test" , "onUnavailable::"+NetworkObserver.NetworkStatus.Unavailable.name)
                    trySend(false)
                }
            }
            val request = NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            connectivityManager.registerNetworkCallback(request , callback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.shareIn(externalScope , started = SharingStarted.Eagerly)
    }

    override fun getStatus() : Boolean {



        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as  ConnectivityManager

        val netInfo = cm.activeNetwork
        return netInfo != null


    }
}