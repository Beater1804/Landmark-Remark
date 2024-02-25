package com.beater.landmarkremark.models

data class ApiError(
    var message: String = "Error",
    val requireUpdate: Boolean = false
)
