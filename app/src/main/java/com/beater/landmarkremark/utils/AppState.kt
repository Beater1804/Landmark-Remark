package com.beater.landmarkremark.utils

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.auth.FirebaseUser

object AppState {
    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    var currentUser: FirebaseUser? = null
}