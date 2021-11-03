package ru.shurick.enterprise.utils.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocationProvider @Inject constructor(
    @ApplicationContext context: Context
) {

    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(
        success: suspend CoroutineScope.(location: Location) -> Unit,
        failure: (e: Exception) -> Unit
    ) {

        val loc: Task<Location> = fusedLocationProviderClient.lastLocation
        loc.addOnSuccessListener {
            GlobalScope.launch(Dispatchers.Default) {
                success(it)
            }
        }
        loc.addOnFailureListener {

            locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 20 * 1000
            }

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    for (location in locationResult.locations) {
                        onDestroy()
                        if (location != null) {
                            GlobalScope.launch(Dispatchers.Default) {
                                success(location)
                            }
                        } else {
                            failure(LocationException("Unable to determine current location"))
                        }
                    }
                }
            }

            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )
        }

    }

    private fun onDestroy() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        locationRequest = null
        locationCallback = null
    }
}

class LocationException(message: String?) : Exception(message)