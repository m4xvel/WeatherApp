package org.m4xvel.weatherapp.ui.permissions

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.koin.androidx.compose.koinViewModel
import org.m4xvel.weatherapp.ui.MainViewModel


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionsScreen(mainViewModel: MainViewModel = koinViewModel()) {

    val geoState by mainViewModel.geoState.collectAsState()

    val locationPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val locationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
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
                        scope.launch(Dispatchers.IO) {
                            val result = locationClient.lastLocation.await()
                            geoState.lat = result?.latitude
                            geoState.lon = result?.longitude
                            mainViewModel.isLoadingAndSetData()
                        }

                    } catch (e: SecurityException) {
                        Toast.makeText(
                            context,
                            "Невозможно определить местоположение!",
                            Toast.LENGTH_SHORT
                        ).show()
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