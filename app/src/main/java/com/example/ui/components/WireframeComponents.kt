package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.ui.theme.*

@Composable
fun WireframeImagePlaceholder(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.Fastfood,
    contentDescription: String = "Image placeholder"
) {
    Box(
        modifier = modifier
            .background(SurfaceContainerHighest, shape = RoundedCornerShape(12.dp))
            .border(1.dp, WireframeOutlineVariant, shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                color = WireframeOutlineVariant.copy(alpha = 0.4f),
                start = Offset(0f, 0f),
                end = Offset(size.width, size.height),
                strokeWidth = 1.dp.toPx()
            )
            drawLine(
                color = WireframeOutlineVariant.copy(alpha = 0.4f),
                start = Offset(size.width, 0f),
                end = Offset(0f, size.height),
                strokeWidth = 1.dp.toPx()
            )
        }
        
        Box(
            modifier = Modifier
                .background(SurfaceContainer, shape = CircleShape)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = OnSurfaceVariantWireframe,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}
