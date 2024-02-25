package com.beater.landmarkremark.models

import com.beater.landmarkremark.utils.AppConfig
import com.beater.landmarkremark.utils.extensions.getErrorMsg
import retrofit2.Response

sealed class UiState<out T> {
    object Default : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T? = null) : UiState<T>()
    class Error<T>(response: Response<T>? = null) : UiState<Nothing>() {
        var errorType: ServiceError

        var errorMsg: String = ""

        init {
            errorType = ServiceError.Specific(response?.errorBody().getErrorMsg())
        }

        companion object {
            fun onException(errorMsg: String = AppConfig.unexpectedErrorMessage): Error<Nothing> {
                return Error<Nothing>().apply {
                    errorType = ServiceError.Specific(errorMsg)
                }
            }
        }
    }
}

sealed class ServiceError {
    data class Specific(val errorMsg: String) : ServiceError()
}