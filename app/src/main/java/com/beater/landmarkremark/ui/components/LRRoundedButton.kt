package com.beater.landmarkremark.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beater.landmarkremark.ui.theme.Gray2
import com.beater.landmarkremark.ui.theme.LRButtonSize
import com.beater.landmarkremark.ui.theme.LRFontWeight
import com.beater.landmarkremark.ui.theme.LRRoundedButtonStyle

@Composable
fun LRRoundedButton(
    title: String,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    paddingHorizontal: Int = 24,
    size: LRButtonSize = LRButtonSize.Secondary,
    style: LRRoundedButtonStyle = LRRoundedButtonStyle.Positive,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = style.containerColor,
            disabledContainerColor = Gray2
        ),
        border = BorderStroke(
            width = if (style == LRRoundedButtonStyle.Negative) 1.dp else 0.dp,
            color = Gray2
        ),
        enabled = enable,
        shape = RoundedCornerShape(1000.dp),
        contentPadding = PaddingValues(horizontal = paddingHorizontal.dp),
        modifier = Modifier
            .height(size.height)
            .then(modifier),
        onClick = onClick
    ) {
        LRText(
            text = title,
            fontSize = size.titleFontSize,
            fontWeight = LRFontWeight.Bold,
            color = style.titleColor,
            maxLines = 1
        )
    }
}