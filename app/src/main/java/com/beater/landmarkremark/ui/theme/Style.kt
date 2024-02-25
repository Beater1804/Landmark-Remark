package com.beater.landmarkremark.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class LRRoundedButtonStyle(val containerColor: Color, val titleColor: Color) {
    object Positive : LRRoundedButtonStyle(containerColor = BlackCustom, titleColor = White)
    object Negative : LRRoundedButtonStyle(containerColor = White, titleColor = BlackCustom)
}

sealed class LRButtonSize(val height: Dp, val titleFontSize: TextUnit) {
    object Primary : LRButtonSize(height = 51.dp, titleFontSize = 20.sp)
    object Secondary : LRButtonSize(height = 41.dp, titleFontSize = 18.sp)
}