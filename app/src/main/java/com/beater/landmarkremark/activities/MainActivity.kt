package com.beater.landmarkremark.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.beater.landmarkremark.ui.navigation.NavGraph
import com.beater.landmarkremark.ui.navigation.NavRoute
import com.beater.landmarkremark.ui.theme.LandmarkRemarkTheme
import com.beater.landmarkremark.utils.AppState
import com.google.android.gms.location.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppState.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            MainScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val navController = rememberNavController()

    LandmarkRemarkTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.padding(it)) {
                NavGraph(
                    navController = navController,
                    startDestination = NavRoute.Splash.route
                )
            }
        }
    }
}
