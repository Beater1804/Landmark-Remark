package com.beater.landmarkremark.utils.extensions

import com.google.firebase.firestore.DocumentSnapshot

fun DocumentSnapshot.containsFieldKeyword(field: String, keyword: String): Boolean {
    val fieldValue = getString(field)?.lowercase() ?: return false
    return fieldValue.contains(keyword.lowercase())
}