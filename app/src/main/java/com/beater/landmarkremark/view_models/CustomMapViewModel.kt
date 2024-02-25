package com.beater.landmarkremark.view_models

import android.location.Location
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beater.landmarkremark.models.Note
import com.beater.landmarkremark.models.UiState
import com.beater.landmarkremark.services.firebase_services.CloudFirestoreService
import com.beater.landmarkremark.utils.AppState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CustomMapViewModel : ViewModel() {
    private val cloudFirestoreService: CloudFirestoreService = CloudFirestoreService()

    private var _addNoteUiState = MutableStateFlow<UiState<Nothing>>(UiState.Default)
    val addNoteUiState get() = _addNoteUiState

    private var _getAllNotesUiState = MutableStateFlow<UiState<Nothing>>(UiState.Loading)
    val getAllNotesUiState get() = _getAllNotesUiState

    private var _allNotes = mutableStateListOf<Note>()
    val allNotes get() = _allNotes

    private var _allLatLng = mutableStateListOf<LatLng>()
    val allLatlng get() = _allLatLng

    fun getAllNotes() {
        viewModelScope.launch {
            try {
                _allNotes.clear()
                _allLatLng.clear()
                _allNotes.addAll(cloudFirestoreService.getAllNotes())
                _allLatLng.addAll(_allNotes.map { note ->
                    LatLng(note.lat, note.long)
                })
                Log.d("ZED", _allLatLng.toString())
                _getAllNotesUiState.value = UiState.Success()
            } catch (e: Exception) {
                Log.d("ZED", e.printStackTrace().toString())
                _getAllNotesUiState.value = UiState.Error.onException()
            }
        }
    }

    fun addNote(title: String, description: String, location: Location) {
        viewModelScope.launch {
            _addNoteUiState.value = UiState.Loading

            try {
                val note = Note(
                    userId = AppState.currentUser?.uid.orEmpty(),
                    email = AppState.currentUser!!.email ?: "",
                    title = title,
                    description = description,
                    lat = location.latitude,
                    long = location.longitude
                )

                cloudFirestoreService.addNote(note)

                _addNoteUiState.value = UiState.Success()
            } catch (e: Exception) {
                _addNoteUiState.value = UiState.Error.onException()
            }
        }
    }

    fun resetGetAllNotesUiState() {
        _getAllNotesUiState.value = UiState.Default
    }

    fun resetAddNoteUiState() {
        _addNoteUiState.value = UiState.Default
    }
}