@file:Suppress("NAME_SHADOWING")

package com.beater.landmarkremark.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beater.landmarkremark.ui.theme.*

private const val SHORT_TEXT_FIELD_LIMIT = 200

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LRBasicTextField(
    value: String,
    placeholder: String,
    isError: Boolean = false,
    readOnly: Boolean = false,
    errorMsg: String = "",
    maxLength: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val maxLength = if (maxLength != Int.MAX_VALUE) maxLength else SHORT_TEXT_FIELD_LIMIT

    BasicTextField(
        value = value,
        onValueChange = { newValue ->
            if (value.length < maxLength || newValue.length < maxLength) {
                onValueChange(newValue)
            }
        },
        readOnly = readOnly,
        maxLines = 1,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(Blue),
        textStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = LRFontWeight.Regular.value,
            lineHeight = 24.sp,
            color = BlackCustom,
        ),
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                isError = isError,
                placeholder = {
                    LRText(
                        text = placeholder,
                        fontSize = 18.sp,
                        color = Gray,
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                supportingText = if (isError) {
                    {
                        LRText(
                            text = errorMsg,
                            color = Red,
                            fontSize = 10.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                } else {
                    null
                },
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                shape = RoundedCornerShape(60.dp),
                singleLine = true,
                enabled = true,
                interactionSource = interactionSource,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Gray3,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Blue,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Blue
                ),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
                container = {
                    TextFieldDefaults.OutlinedBorderContainerBox(
                        enabled = true,
                        isError = isError,
                        interactionSource = interactionSource,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Gray3,
                            unfocusedIndicatorColor = Gray2,
                            focusedIndicatorColor = Blue
                        ),
                        shape = RoundedCornerShape(60.dp)
                    )
                }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
    )
}