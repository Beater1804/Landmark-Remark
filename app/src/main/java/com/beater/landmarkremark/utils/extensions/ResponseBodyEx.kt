package com.beater.landmarkremark.utils.extensions

import com.beater.landmarkremark.models.ApiError
import com.beater.landmarkremark.utils.AppConfig
import com.google.gson.Gson
import okhttp3.ResponseBody

fun ResponseBody?.getErrorMsg(): String {
    this?.let {
        return Gson().fromJson(it.string(), ApiError::class.java).message
    }
    return AppConfig.unexpectedErrorMessage
}