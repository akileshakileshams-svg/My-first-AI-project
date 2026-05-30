package com.example.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.CartItem
import com.example.ui.components.WireframeImagePlaceholder
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    cartItems: List<CartItem>,
    subtotal: Double,
    deliveryFee: Double,
    taxesAndFees: Double,
    total: Double,
    onBackClick: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onIncrement: (CartItem) -> Unit,
    onDecrement: (CartItem) -> Unit,
    onNavigateToCheckout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Cart",
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
            if (cartItems.isNotEmpty()) {
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
                            onClick = onNavigateToCheckout,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("checkout_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryWireframe),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Checkout",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnPrimaryWireframe
                                )
                                Text(
                                    text = String.format("$%.2f", total),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnPrimaryWireframe
                                )
                            }
                        }
                    }
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        if (cartItems.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WireframeBackground)
                    .padding(innerPadding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingBag,
                    contentDescription = "Empty cart indicator",
                    tint = WireframeOutlineVariant,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Welcome to your Cart!",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = WireframeOnBackground
                )
                Text(
                    text = "Add some of our delicious food dishes from popular restaurants to get started.",
                    fontSize = 14.sp,
                    color = OnSurfaceVariantWireframe,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
                )
                Button(
                    onClick = onNavigateToSearch,
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryWireframe)
                ) {
                    Text("Explore Menu")
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WireframeBackground)
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp)
            ) {
                // Delivery Address Information Layer
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceContainerLowest, shape = RoundedCornerShape(12.dp))
                            .border(1.dp, WireframeOutlineVariant, shape = RoundedCornerShape(12.dp))
                            .padding(16.dp)
                            .padding(bottom = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Delivery Address",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = WireframeOnBackground
                            )
                            Text(
                                text = "Change",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = OnSurfaceVariantWireframe,
                                modifier = Modifier
                                    .underline()
                                    .clickable { /* Prototyped address picker */ }
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Pins",
                                tint = WireframeOutline,
                                modifier = Modifier.size(18.dp)
                            )
                            Column {
                                Text(
                                    text = "123 Main Street, Apt 4B",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = WireframeOnBackground
                                )
                                Text(
                                    text = "Delivery time: 30-45 mins",
                                    fontSize = 12.sp,
                                    color = OnSurfaceVariantWireframe
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Header for itemized checklist
                item {
                    Text(
                        text = "Your Items",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = WireframeOnBackground,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }

                // Itemized custom list inside database
                items(cartItems) { item ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            // Food picture vector representation
                            WireframeImagePlaceholder(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                icon = Icons.Default.Fastfood,
                                contentDescription = "Cart thumbnail representation"
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = item.name,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = WireframeOnBackground,
                                        modifier = Modifier.weight(1f),
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = String.format("$%.2f", item.price * item.quantity),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = WireframeOnBackground,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }

                                if (item.extras.isNotBlank()) {
                                    Text(
                                        text = item.extras,
                                        fontSize = 12.sp,
                                        color = OnSurfaceVariantWireframe,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    )
                                }

                                if (item.notes.isNotBlank()) {
                                    Text(
                                        text = "Note: ${item.notes}",
                                        fontSize = 12.sp,
                                        color = OnSurfaceVariantWireframe,
                                        modifier = Modifier.padding(bottom = 6.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                // Quantity Editors
                                Row(
                                    modifier = Modifier
                                        .background(SurfaceContainerLowest, shape = RoundedCornerShape(8.dp))
                                        .border(1.dp, WireframeOutlineVariant, shape = RoundedCornerShape(8.dp))
                                        .padding(horizontal = 4.dp, vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    IconButton(
                                        onClick = { onDecrement(item) },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Remove,
                                            contentDescription = "Minus",
                                            tint = PrimaryWireframe,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }

                                    Text(
                                        text = item.quantity.toString(),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = WireframeOnBackground,
                                        modifier = Modifier.width(20.dp),
                                        textAlign = TextAlign.Center
                                    )

                                    IconButton(
                                        onClick = { onIncrement(item) },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Plus",
                                            tint = PrimaryWireframe,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                        Divider(color = WireframeOutlineVariant, modifier = Modifier.fillMaxWidth())
                    }
                }

                // "Add more items" navigation trigger
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToSearch() }
                            .padding(vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Add circle",
                            tint = OnSurfaceVariantWireframe
                        )
                        Text(
                            text = "Add more items",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurfaceVariantWireframe
                        )
                    }
                }

                // Consolidated pricing details (Receipt summary card)
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceContainerLow, shape = RoundedCornerShape(12.dp))
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
                            Text(text = String.format("$%.2f", subtotal), fontSize = 14.sp, color = WireframeOnBackground)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Delivery Fee", fontSize = 14.sp, color = OnSurfaceVariantWireframe)
                            Text(text = String.format("$%.2f", deliveryFee), fontSize = 14.sp, color = WireframeOnBackground)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Taxes & Fees", fontSize = 14.sp, color = OnSurfaceVariantWireframe)
                            Text(text = String.format("$%.2f", taxesAndFees), fontSize = 14.sp, color = WireframeOnBackground)
                        }

                        Divider(color = WireframeOutlineVariant.copy(alpha = 0.5f))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Total", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = WireframeOnBackground)
                            Text(text = String.format("$%.2f", total), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = WireframeOnBackground)
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

// Ext helper for Composable underlines
fun Modifier.underline() = this.drawBehind {
    val strokeWidth = 1.dp.toPx()
    val y = size.height - strokeWidth
    drawLine(
        color = WireframeOutline,
        start = androidx.compose.ui.geometry.Offset(0f, y),
        end = androidx.compose.ui.geometry.Offset(size.width, y),
        strokeWidth = strokeWidth
    )
}
