package com.beater.landmarkremark.models.ui_actions

sealed class AddNewNoteAction {
    object OnClose : AddNewNoteAction()
    data class OnSave(val title: String, val description: String) : AddNewNoteAction()
}

typealias AddNewNoteCallback = (AddNewNoteAction) -> Unit
