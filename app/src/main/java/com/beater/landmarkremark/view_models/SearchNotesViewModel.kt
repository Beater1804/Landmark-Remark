package com.beater.landmarkremark.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beater.landmarkremark.models.Note
import com.beater.landmarkremark.models.UiState
import com.beater.landmarkremark.services.firebase_services.CloudFirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SearchNotesViewModel : ViewModel() {
    private val cloudFirestoreService: CloudFirestoreService = CloudFirestoreService()

    private var _getSearchNotesUiState = MutableStateFlow<UiState<Nothing>>(UiState.Default)
    val getSearchNotesUiState get() = _getSearchNotesUiState

    private var _searchNotes: List<Note> = emptyList()
    val searchNotes get() = _searchNotes

    fun searchNote(keyword: String) {
        _getSearchNotesUiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                _searchNotes = cloudFirestoreService.searchNote(keyword)
                _getSearchNotesUiState.value = UiState.Success()
            } catch (e: Exception){
                _getSearchNotesUiState.value = UiState.Error.onException()
            }
        }
    }

    fun resetGetSearchNotesUiState() {
        _getSearchNotesUiState.value = UiState.Default
    }
}