package com.beater.landmarkremark.ui.screens.my_notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beater.landmarkremark.models.Note
import com.beater.landmarkremark.ui.components.LRText
import com.beater.landmarkremark.ui.theme.BlackCustom
import com.beater.landmarkremark.ui.theme.Gray2
import com.beater.landmarkremark.ui.theme.LRFontWeight

@Composable
fun NoteItem(
    note: Note,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Gray2)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LRText(
            text = note.title,
            fontSize = 22.sp,
            fontWeight = LRFontWeight.Bold,
            color = BlackCustom
        )

        LRText(
            text = note.description
        )
    }
}