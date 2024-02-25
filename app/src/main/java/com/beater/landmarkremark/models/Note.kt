package com.beater.landmarkremark.models

import com.google.firebase.Timestamp

data class Note(
    val userId: String = "",
    val email: String = "",
    val title: String = "",
    val description: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val lat: Double = 0.0,
    val long: Double = 0.0
)