package com.beater.landmarkremark.utils.helpers

import android.content.Context
import com.beater.landmarkremark.utils.APP_STORAGE_DATA_KEY
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson

object AppHelper {
    private const val USER_FIREBASE_KEY = "USER_FIREBASE_KEY"

    fun saveUser(context: Context, user: FirebaseUser? = null) {
        val userData = user?.let {
            Gson().toJson(user)
        } ?: run {
            ""
        }

        context.getSharedPreferences(APP_STORAGE_DATA_KEY, Context.MODE_PRIVATE)
            .edit()
            .putString(USER_FIREBASE_KEY, userData)
            .apply()
    }

    fun getUser(context: Context): FirebaseUser? {
        var user: FirebaseUser? = null
        val userJson = context.getSharedPreferences(APP_STORAGE_DATA_KEY, Context.MODE_PRIVATE)
            .getString(USER_FIREBASE_KEY, "")

        userJson?.let {
            user = Gson().fromJson(it, FirebaseUser::class.java)
        }
        return user
    }
}