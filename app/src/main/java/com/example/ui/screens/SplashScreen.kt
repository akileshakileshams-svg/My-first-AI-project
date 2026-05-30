package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.WireframeImagePlaceholder
import com.example.ui.theme.OnSurfaceVariantWireframe
import com.example.ui.theme.PrimaryWireframe
import com.example.ui.theme.WireframeBackground

@Composable
fun SplashScreen(
    onGetStarted: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(WireframeBackground)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Centered Branding Identity
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                WireframeImagePlaceholder(
                    modifier = Modifier
                        .size(120.dp)
                        .padding(bottom = 16.dp),
                    icon = Icons.Default.Fastfood,
                    contentDescription = "FoodieGo logo placeholder"
                )

                Text(
                    text = "FoodieGo",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryWireframe,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Your structural blueprint for mobile food delivery architecture.",
                    fontSize = 14.sp,
                    color = OnSurfaceVariantWireframe,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.widthIn(max = 240.dp)
                )
            }

            // Bottom Action Area
            Button(
                onClick = onGetStarted,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("get_started_button"),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryWireframe)
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
