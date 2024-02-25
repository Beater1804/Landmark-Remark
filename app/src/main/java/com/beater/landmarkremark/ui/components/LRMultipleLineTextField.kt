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
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beater.landmarkremark.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LRMultipleLineTextField(
    value: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 14.sp,
    isError: Boolean = false,
    readOnly: Boolean = false,
    errorMsg: String = "",
    maxLength: Int = Int.MAX_VALUE,
    maxLines: Int = Int.MAX_VALUE,
    unfocusedIndicatorColor: Color = Gray2,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable () -> Unit = {},
    onValueChange: (String) -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    BasicTextField(
        value = value,
        onValueChange = { newValue ->
            if (maxLength == Int.MAX_VALUE || value.length < maxLength || newValue.length < maxLength) {
                onValueChange(newValue)
            }
        },
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(Blue),
        textStyle = TextStyle(
            fontSize = fontSize,
            fontWeight = LRFontWeight.Regular.value,
            lineHeight = 24.sp,
            color = BlackCustom,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            )
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
                        fontSize = fontSize,
                        color = Gray,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                supportingText = {
                    if (isError) {
                        LRText(
                            text = errorMsg,
                            color = Red,
                            fontSize = 10.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                trailingIcon = trailingIcon,
                shape = RoundedCornerShape(6.dp),
                singleLine = false,
                enabled = true,
                interactionSource = interactionSource,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Gray3,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Blue,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Blue
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                container = {
                    TextFieldDefaults.OutlinedBorderContainerBox(
                        enabled = true,
                        isError = isError,
                        interactionSource = interactionSource,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Gray3,
                            unfocusedIndicatorColor = unfocusedIndicatorColor,
                            focusedIndicatorColor = Blue
                        ),
                        shape = RoundedCornerShape(6.dp)
                    )
                }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    )
}