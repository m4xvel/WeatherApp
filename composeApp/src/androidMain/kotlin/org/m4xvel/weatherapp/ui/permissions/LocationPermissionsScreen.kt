package org.m4xvel.weatherapp.ui.permissions

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionsScreen() {
    val locationPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )
    Row(
        modifier = Modifier.fillMaxWidth(0.9f)
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            modifier = Modifier.size(54.dp)
                .border(color = Color.Black, width = 2.dp, shape = CircleShape),
            onClick = {
                if (locationPermissionState.status.shouldShowRationale) {
                    locationPermissionState.launchPermissionRequest()
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