package org.m4xvel.weatherapp.ui.permissions

import android.os.Looper
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import org.koin.androidx.compose.koinViewModel
import org.m4xvel.weatherapp.ui.MainViewModel


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionButton(mainViewModel: MainViewModel = koinViewModel()) {

    val locationPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val context = LocalContext.current

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    val locationRequest = LocationRequest.Builder(1).build()

    Row(
        modifier = Modifier.fillMaxWidth(0.9f)
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            modifier = Modifier.size(54.dp)
                .border(color = Color.Black, width = 2.dp, shape = CircleShape),
            onClick = {
                if (locationPermissionState.status.isGranted) {
                    try {
                        fusedLocationClient.requestLocationUpdates(
                            locationRequest,
                            object : LocationCallback() {
                                override fun onLocationResult(locationResult: LocationResult) {
                                    for (location in locationResult.locations) {
                                        mainViewModel.setDataLocation(
                                            location.latitude,
                                            location.longitude
                                        )
                                    }
                                }
                            },
                            Looper.getMainLooper()
                        )

                    } catch (e: SecurityException) {
                        Log.d("MyTag", "Error: ${e.localizedMessage}")
                    }
                } else {
                    locationPermissionState.launchPermissionRequest()
                }
            },
        ) {
            Icon(
                Icons.Outlined.LocationOn, contentDescription = "LocationOn",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}