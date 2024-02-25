package com.beater.landmarkremark.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import com.beater.landmarkremark.utils.extensions.noRippleClickable

@Composable
fun LRCommonScreen(
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.() -> Unit
) {
    val focusManager = LocalFocusManager.current

    Box(
        contentAlignment = contentAlignment,
        modifier = Modifier
            .fillMaxSize()
            .noRippleClickable {
                focusManager.clearFocus()
            }
    ) {
        content()
    }
}