package com.beater.landmarkremark.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beater.landmarkremark.models.Note
import com.beater.landmarkremark.models.UiState
import com.beater.landmarkremark.services.firebase_services.CloudFirestoreService
import com.beater.landmarkremark.utils.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MyNotesViewModel : ViewModel() {
    private val cloudFirestoreService: CloudFirestoreService = CloudFirestoreService()

    private var _getMyNotesUiState = MutableStateFlow<UiState<Nothing>>(UiState.Loading)
    val getMyNotesUiState get() = _getMyNotesUiState

    private var _notes: List<Note> = emptyList()
    val notes get() = _notes

    fun getMyNotes(userId: String) {
        viewModelScope.launch {
            try {
                _notes = cloudFirestoreService.getUserNotes(userId)
                _getMyNotesUiState.value = UiState.Success()
            } catch (e: Exception){
                _getMyNotesUiState.value = UiState.Error.onException()
            }
        }
    }

    fun signOut() {
        AppState.currentUser = null
        cloudFirestoreService.signOut()
    }

    fun resetGetMyNotesUiState() {
        _getMyNotesUiState.value = UiState.Default
    }
}