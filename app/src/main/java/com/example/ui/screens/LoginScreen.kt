package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "FoodieGo",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryWireframe
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceContainerLowest)
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Welcome Section
            Text(
                text = "Welcome Back",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = WireframeOnBackground,
                modifier = Modifier.padding(bottom = 4.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Please log in to continue.",
                fontSize = 14.sp,
                color = OnSurfaceVariantWireframe,
                modifier = Modifier.padding(bottom = 32.dp),
                textAlign = TextAlign.Center
            )

            // Email Form Input
            Text(
                text = "Email Address",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = WireframeOnBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("name@example.com", color = WireframeOutlineVariant) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email icon",
                        tint = WireframeOutlineVariant
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("email_input")
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryWireframe,
                    unfocusedBorderColor = WireframeOutlineVariant
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            // Password Form Input
            Text(
                text = "Password",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = WireframeOnBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("••••••••", color = WireframeOutlineVariant) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Lock icon",
                        tint = WireframeOutlineVariant
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("password_input")
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryWireframe,
                    unfocusedBorderColor = WireframeOutlineVariant
                ),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            // Forgot Password Link Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "Forgot Password?",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = OnSurfaceVariantWireframe,
                    modifier = Modifier.clickable { /* Prototyped action */ }
                )
            }

            // Primary Log In Button
            Button(
                onClick = onLoginSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("login_button"),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryWireframe),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Login",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Separator "OR" divider
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(modifier = Modifier.weight(1f), color = WireframeOutlineVariant)
                Text(
                    text = "OR",
                    fontSize = 12.sp,
                    color = OnSurfaceVariantWireframe,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Divider(modifier = Modifier.weight(1f), color = WireframeOutlineVariant)
            }

            // Sign Up Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account?",
                    fontSize = 14.sp,
                    color = OnSurfaceVariantWireframe
                )
                Text(
                    text = "Sign Up",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryWireframe,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable { onLoginSuccess() }
                )
            }
        }
    }
}
