package com.beater.landmarkremark.ui.screens.my_notes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beater.landmarkremark.models.Note

@Composable
fun NoteList(
    list: List<Note>,
    modifier: Modifier = Modifier,
    onClick: (Note) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = list,
            key = { notes ->
                notes.timestamp
            }
        ) { note ->
            NoteItem(
                note = note,
                onClick = {
                    onClick(note)
                }
            )
        }
    }
}