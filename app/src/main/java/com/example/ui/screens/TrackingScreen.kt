package com.example.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.OrderEntity
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackingScreen(
    order: OrderEntity?,
    onNavigateToHome: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToOrders: () -> Unit,
    onNavigateToCart: () -> Unit
) {
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
                navigationIcon = {
                    IconButton(onClick = onNavigateToHome) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToCart) {
                        Icon(imageVector = Icons.Default.ShoppingBag, contentDescription = "Cart")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceContainerLowest)
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = SurfaceContainerLowest,
                tonalElevation = 0.dp
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToHome,
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home", fontSize = 10.sp) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToSearch,
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    label = { Text("Search", fontSize = 10.sp) }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = onNavigateToOrders,
                    icon = { Icon(Icons.Default.Assignment, contentDescription = "Orders") },
                    label = { Text("Orders", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryWireframe,
                        selectedTextColor = PrimaryWireframe,
                        indicatorColor = SurfaceContainerHigh
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile", fontSize = 10.sp) }
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WireframeBackground)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Simulated Map Drawing Area (Top half)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(SurfaceContainerHigh)
            ) {
                // Drawing delivery vectors
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    
                    // Curved Dotted Line representing GPS Path
                    drawCircle(
                        color = PrimaryWireframe.copy(alpha = 0.05f),
                        center = center,
                        radius = size.width / 3f
                    )
                    
                    // Draw GPS trajectory path
                    drawLine(
                        color = PrimaryWireframe,
                        start = Offset(size.width * 0.2f, size.height * 0.7f),
                        end = Offset(size.width * 0.8f, size.height * 0.3f),
                        strokeWidth = 3.dp.toPx(),
                        pathEffect = strokeEffect
                    )
                }

                // GPS Marker Nodes
                // 1. Restaurant (Storefront symbol)
                Box(
                    modifier = Modifier
                        .offset(x = 60.dp, y = 120.dp)
                        .background(PrimaryWireframe, shape = CircleShape)
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Storefront,
                        contentDescription = "Restaurant Marker",
                        tint = OnPrimaryWireframe,
                        modifier = Modifier.size(16.dp)
                    )
                }

                // 2. Delivery Rider (Moving bicycle symbol in center coordinates)
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(SurfaceContainerLowest, shape = CircleShape)
                        .border(1.dp, WireframeOutline, shape = CircleShape)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsBike,
                        contentDescription = "Rider Marker",
                        tint = PrimaryWireframe,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // 3. User destination (Location Pin symbol)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-60).dp, y = 40.dp)
                        .background(SurfaceContainerLowest, shape = CircleShape)
                        .border(1.dp, WireframeOutline, shape = CircleShape)
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Destination Marker",
                        tint = PrimaryWireframe,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Bottom Timeline Section resembling Sheet panel overlays
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceContainerLowest, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .border(
                        BorderStroke(1.dp, WireframeOutlineVariant),
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(20.dp)
            ) {
                // Central drag handle bar representation
                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(4.dp)
                        .background(WireframeOutlineVariant, shape = RoundedCornerShape(2.dp))
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Arriving Header details
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        val activeETA = when (order?.status) {
                            "CONFIRMED" -> "Arriving in 35 min"
                            "PREPARING" -> "Arriving in 25 min"
                            "OUT_FOR_DELIVERY" -> "Arriving in 15 min"
                            "DELIVERED" -> "Delivered!"
                            else -> "Arriving in 15 min"
                        }
                        Text(
                            text = activeETA,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = WireframeOnBackground
                        )
                        Text(
                            text = "Estimated delivery by 7:45 PM",
                            fontSize = 13.sp,
                            color = OnSurfaceVariantWireframe
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(SurfaceContainerHigh, shape = RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        val badgeLabel = when (order?.status) {
                            "CONFIRMED" -> "Ordered"
                            "PREPARING" -> "Preparing"
                            "OUT_FOR_DELIVERY" -> "On the way"
                            "DELIVERED" -> "Delivered"
                            else -> "On the way"
                        }
                        Text(
                            text = badgeLabel,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryWireframe
                        )
                    }
                }

                Divider(color = WireframeOutlineVariant, modifier = Modifier.padding(bottom = 20.dp))

                // Custom Timeline Stepper Components
                val orderStatus = order?.status ?: "OUT_FOR_DELIVERY"
                val statuses = listOf(
                    Triple("Order Confirmed", "7:00 PM", "CONFIRMED"),
                    Triple("Preparing your food", "7:05 PM", "PREPARING"),
                    Triple("Out for Delivery", "Driver picked up order", "OUT_FOR_DELIVERY"),
                    Triple("Delivered", "Ready to enjoy", "DELIVERED")
                )

                Column(
                    modifier = Modifier.padding(start = 8.dp, bottom = 24.dp)
                ) {
                    statuses.forEachIndexed { index, (label, desc, key) ->
                        val isCompleted = isStatusCompleted(orderStatus, key)
                        val isActive = orderStatus == key

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            // Column for vertical stepper bullet nodes
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.width(24.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(
                                            color = if (isCompleted || isActive) PrimaryWireframe else Color.Transparent,
                                            shape = CircleShape
                                        )
                                        .border(
                                            width = 2.dp,
                                            color = if (isCompleted || isActive) PrimaryWireframe else WireframeOutlineVariant,
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isActive) {
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .background(OnPrimaryWireframe, shape = CircleShape)
                                        )
                                    } else if (isCompleted) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "done",
                                            tint = OnPrimaryWireframe,
                                            modifier = Modifier.size(10.dp)
                                        )
                                    }
                                }

                                if (index < statuses.lastIndex) {
                                    Box(
                                        modifier = Modifier
                                            .width(2.dp)
                                            .height(36.dp)
                                            .background(
                                                if (isCompleted) PrimaryWireframe else WireframeOutlineVariant
                                            )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(
                                    text = label,
                                    fontSize = 14.sp,
                                    fontWeight = if (isActive || isCompleted) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isActive || isCompleted) WireframeOnBackground else OnSurfaceVariantWireframe
                                )
                                Text(
                                    text = desc,
                                    fontSize = 12.sp,
                                    color = OnSurfaceVariantWireframe
                                )
                            }
                        }
                    }
                }

                // Delivery Partner profile card
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceContainerLow, shape = RoundedCornerShape(12.dp))
                        .border(1.dp, WireframeOutlineVariant, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(SurfaceContainerHigh, shape = CircleShape)
                                .border(1.dp, WireframeOutlineVariant, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Partner Avatar",
                                tint = OnSurfaceVariantWireframe
                            )
                        }

                        Column {
                            Text(
                                text = "Alex M.",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = WireframeOnBackground
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "rating",
                                    tint = PrimaryWireframe,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "4.9 (120+ deliveries)",
                                    fontSize = 12.sp,
                                    color = OnSurfaceVariantWireframe
                                )
                            }
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(
                            onClick = {},
                            modifier = Modifier
                                .size(40.dp)
                                .background(SurfaceContainerLowest, shape = CircleShape)
                                .border(1.dp, WireframeOutlineVariant, shape = CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = "Call",
                                tint = PrimaryWireframe,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        IconButton(
                            onClick = {},
                            modifier = Modifier
                                .size(40.dp)
                                .background(SurfaceContainerLowest, shape = CircleShape)
                                .border(1.dp, WireframeOutlineVariant, shape = CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = "Chat",
                                tint = PrimaryWireframe,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                // Order metadata details
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Order #${order?.orderId ?: "FD-8924"}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = WireframeOnBackground
                        )
                        Text(
                            text = "View Receipt",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = PrimaryWireframe,
                            modifier = Modifier
                                .underline()
                                .clickable { /* view details receipt action */ }
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = order?.itemsSummary ?: "2 Items • Burger Joint",
                        fontSize = 12.sp,
                        color = OnSurfaceVariantWireframe
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Delivering to home",
                            tint = OnSurfaceVariantWireframe,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Delivering to Work",
                            fontSize = 12.sp,
                            color = OnSurfaceVariantWireframe
                        )
                    }
                }
            }
        }
    }
}

// Stepper completed nodes ordering mapping logic helper
private fun isStatusCompleted(currentStatus: String, stepStatus: String): Boolean {
    val ranking = listOf("CONFIRMED", "PREPARING", "OUT_FOR_DELIVERY", "DELIVERED")
    val currentIndex = ranking.indexOf(currentStatus)
    val stepIndex = ranking.indexOf(stepStatus)
    return stepIndex < currentIndex
}
