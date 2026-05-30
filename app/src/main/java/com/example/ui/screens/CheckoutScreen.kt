package com.example.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    currentSubtotal: Double,
    currentDelivery: Double,
    currentTaxes: Double,
    currentTotal: Double,
    onBackClick: () -> Unit,
    onPlaceOrder: (String, String) -> Unit,
    onNavigateToTracking: () -> Unit
) {
    var selectedPayment by remember { mutableStateOf("UPI") }
    val mockAddress = "123 Design System Way, Apt 4B, Wireframe City, WC 90210"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Checkout",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryWireframe
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    Spacer(modifier = Modifier.width(40.dp)) // Equalizer spacer
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceContainerLowest)
            )
        },
        bottomBar = {
            Surface(
                color = SurfaceContainerLowest,
                shadowElevation = 8.dp,
                border = BorderStroke(1.dp, WireframeOutlineVariant)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .navigationBarsPadding()
                ) {
                    Button(
                        onClick = {
                            onPlaceOrder(selectedPayment, mockAddress)
                            onNavigateToTracking()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("place_order_checkout_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryWireframe),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Place Order",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnPrimaryWireframe
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "|",
                                fontSize = 16.sp,
                                color = OnPrimaryWireframe.copy(alpha = 0.5f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = String.format("$%.2f", currentTotal),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnPrimaryWireframe
                            )
                        }
                    }
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WireframeBackground)
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Delivery Address Segment Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceContainerLowest, shape = RoundedCornerShape(12.dp))
                    .border(1.dp, WireframeOutlineVariant, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Delivery Address",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = WireframeOnBackground
                    )
                    Text(
                        text = "Edit",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryWireframe,
                        modifier = Modifier.clickable { /* address editor */ }
                    )
                }

                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "location icon",
                        tint = OnSurfaceVariantWireframe,
                        modifier = Modifier.size(20.dp)
                    )

                    Column {
                        Text(
                            text = "123 Design System Way",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = WireframeOnBackground
                        )
                        Text(
                            text = "Apt 4B, Wireframe City, WC 90210",
                            fontSize = 13.sp,
                            color = OnSurfaceVariantWireframe
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Instructions: Leave at door",
                            fontSize = 12.sp,
                            color = OnSurfaceVariantWireframe,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Payment Methods Selection Layer
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceContainerLowest, shape = RoundedCornerShape(12.dp))
                    .border(1.dp, WireframeOutlineVariant, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Payment Method",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = WireframeOnBackground,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                val payments = listOf(
                    "UPI" to Icons.Default.AccountBalanceWallet,
                    "Credit/Debit Card" to Icons.Default.CreditCard,
                    "Cash on Delivery" to Icons.Default.Payments
                )

                payments.forEach { pair ->
                    val option = pair.first
                    val icon = pair.second
                    val isSelected = selectedPayment == option
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedPayment = option }
                            .padding(vertical = 12.dp)
                            .background(
                                if (isSelected) SurfaceContainerLow else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedPayment = option },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = PrimaryWireframe,
                                    unselectedColor = WireframeOutlineVariant
                                )
                            )

                            Icon(
                                imageVector = icon,
                                contentDescription = option,
                                tint = OnSurfaceVariantWireframe,
                                modifier = Modifier.size(20.dp)
                            )

                            Column {
                                Text(
                                    text = option,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = WireframeOnBackground
                                )
                                if (option == "Credit/Debit Card") {
                                    Text(
                                        text = "Ends in 4242",
                                        fontSize = 11.sp,
                                        color = OnSurfaceVariantWireframe
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Order Cost breakdowns card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceContainerLowest, shape = RoundedCornerShape(12.dp))
                    .border(1.dp, WireframeOutlineVariant, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Order Summary",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = WireframeOnBackground,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Subtotal", fontSize = 14.sp, color = OnSurfaceVariantWireframe)
                    Text(text = String.format("$%.2f", currentSubtotal), fontSize = 14.sp, color = WireframeOnBackground)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Delivery Fee", fontSize = 14.sp, color = OnSurfaceVariantWireframe)
                    Text(text = String.format("$%.2f", currentDelivery), fontSize = 14.sp, color = WireframeOnBackground)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Taxes", fontSize = 14.sp, color = OnSurfaceVariantWireframe)
                    Text(text = String.format("$%.2f", currentTaxes), fontSize = 14.sp, color = WireframeOnBackground)
                }

                Divider(color = WireframeOutlineVariant.copy(alpha = 0.5f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Total", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = WireframeOnBackground)
                    Text(text = String.format("$%.2f", currentTotal), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = WireframeOnBackground)
                }
            }
        }
    }
}
