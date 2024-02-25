package com.beater.landmarkremark.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.beater.landmarkremark.R

@Composable
fun LRSearchBox(
    value: String,
    placeholder: String = stringResource(R.string.search),
    modifier: Modifier,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
    ) {
        LRBasicTextField(
            value = value,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions {
                focusManager.clearFocus()
            },
            placeholder = placeholder,
            onValueChange = onValueChange
        )
    }
}