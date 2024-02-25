package com.beater.landmarkremark.utils.extensions

import com.google.firebase.firestore.DocumentSnapshot

fun DocumentSnapshot.containsEmailKeyword(keyword: String): Boolean {
    val email = getString("email")?.lowercase() ?: return false
    return email.contains(keyword.lowercase())
}

fun DocumentSnapshot.containsTitleKeyword(keyword: String): Boolean {
    val title = getString("title")?.lowercase() ?: return false
    return title.contains(keyword.lowercase())
}

fun DocumentSnapshot.containsDescriptionKeyword(keyword: String): Boolean {
    val description = getString("description")?.lowercase() ?: return false
    return description.contains(keyword.lowercase())
}