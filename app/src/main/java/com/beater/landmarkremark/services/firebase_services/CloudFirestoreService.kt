package com.beater.landmarkremark.services.firebase_services

import android.util.Log
import com.beater.landmarkremark.models.Note
import com.beater.landmarkremark.utils.extensions.containsDescriptionKeyword
import com.beater.landmarkremark.utils.extensions.containsEmailKeyword
import com.beater.landmarkremark.utils.extensions.containsTitleKeyword
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

const val NOTES_COLLECTION_REF = "notes"

class CloudFirestoreService {
    private val notesRef: CollectionReference = Firebase
        .firestore.collection(NOTES_COLLECTION_REF)

    suspend fun getAllNotes(): List<Note> {
        return try {
            return notesRef
                .get()
                .await()
                .documents
                .map { it.toObject(Note::class.java) ?: Note() }
        } catch (e: Exception) {
            Log.d("ZED", e.printStackTrace().toString())
            emptyList()
        }
    }

    suspend fun getUserNotes(
        userId: String,
    ): List<Note> {
        return try {
            return notesRef
                .whereEqualTo("userId", userId)
                .get()
                .await()
                .documents
                .map { it.toObject(Note::class.java) ?: Note() }
        } catch (e: Exception) {
            Log.d("ZED", e.printStackTrace().toString())
            emptyList()
        }
    }

    suspend fun addNote(
        note: Note
    ) {
        try {
            notesRef.add(note).await()
        } catch (e: Exception) {
            Log.d("ZED", e.printStackTrace().toString())
        }
    }

    suspend fun searchNote(keyword: String): List<Note> {
        val uniqueNotes = mutableSetOf<Note>()

        return try {
            // Search compare email
            val notesWithEmail = notesRef
                .get()
                .await()
                .documents
                .filter { it.containsEmailKeyword(keyword) }
                .map { it.toObject(Note::class.java) ?: Note() }

            uniqueNotes.addAll(notesWithEmail)

            // Search compare title
            val notesWithTitle = notesRef
                .get()
                .await()
                .documents
                .filter { it.containsTitleKeyword(keyword) }
                .map { it.toObject(Note::class.java) ?: Note() }

            uniqueNotes.addAll(notesWithTitle)

            // Search compare description
            val notesWithDescription = notesRef
                .get()
                .await()
                .documents
                .filter { it.containsDescriptionKeyword(keyword) }
                .map { it.toObject(Note::class.java) ?: Note() }

            uniqueNotes.addAll(notesWithDescription)

            uniqueNotes.toList()
        } catch (e: Exception) {
            Log.d("ZED", e.printStackTrace().toString())
            emptyList()
        }
    }

    fun signOut() = Firebase.auth.signOut()
}