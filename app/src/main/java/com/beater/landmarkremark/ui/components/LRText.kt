package com.beater.landmarkremark.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.beater.landmarkremark.ui.theme.LRFontWeight

@Composable
fun LRText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    fontSize: TextUnit = 14.sp,
    fontWeight: LRFontWeight = LRFontWeight.Regular,
    textAlign: TextAlign? = null,
    onTextLayout: (TextLayoutResult) -> Unit = {}
) {
    Text(
        text = text,
        color = color,
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = fontWeight.value,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            )
        ),
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        textAlign = textAlign,
        modifier = modifier,
        onTextLayout = onTextLayout
    )
}