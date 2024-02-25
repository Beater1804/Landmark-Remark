package com.beater.landmarkremark.ui.screens.custom_map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.beater.landmarkremark.ui.components.LRIconButton
import com.beater.landmarkremark.ui.theme.Gray2
import com.beater.landmarkremark.ui.theme.White

@Composable
fun GpsIconButton(onIconClick: () -> Unit, onEditClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End,
    ) {
        Column {
            LRIconButton(
                icon = Icons.Default.LocationOn,
                tint = White,
                iconSize = 20.dp,
                modifier = Modifier
                    .padding(end = 20.dp, bottom = 36.dp)
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Gray2)
            ) {
                onIconClick()
            }

            Spacer(modifier = Modifier.height(16.dp))

            LRIconButton(
                icon = Icons.Default.Edit,
                tint = White,
                iconSize = 20.dp,
                modifier = Modifier
                    .padding(end = 20.dp, bottom = 36.dp)
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Gray2)
            ) {
                onEditClick()
            }
        }
    }
}