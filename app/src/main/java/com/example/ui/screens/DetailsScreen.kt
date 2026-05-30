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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FoodItem
import com.example.ui.components.WireframeImagePlaceholder
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    foodItem: FoodItem?,
    restaurantName: String,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit,
    onBackClick: () -> Unit,
    onAddToCart: (FoodItem, Int, List<String>, String) -> Unit,
    onNavigateToCart: () -> Unit
) {
    if (foodItem == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Food item not found.")
        }
        return
    }

    // Interactive selections
    var quantity by remember { mutableStateOf(1) }
    val selectedExtras = remember { mutableStateListOf<String>() }
    var notes by remember { mutableStateOf("") }

    // Dynamic price logic based on extras selections
    val basePrice = foodItem.price
    val extraCheesePrice = if (selectedExtras.contains("Extra Cheese")) 1.50 else 0.0
    val baconPrice = if (selectedExtras.contains("Bacon")) 2.00 else 0.0
    val unitPrice = basePrice + extraCheesePrice + baconPrice
    val finalPrice = unitPrice * quantity

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Details",
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
                    IconButton(onClick = onFavoriteToggle) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite toggle",
                            tint = if (isFavorite) Color.Red else OnSurfaceVariantWireframe
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceContainerLowest)
            )
        },
        bottomBar = {
            // Fix bottom bar containing Quantity Selector and Place/Add button
            Surface(
                color = SurfaceContainerLowest,
                shadowElevation = 8.dp,
                border = BorderStroke(1.dp, WireframeOutlineVariant)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .navigationBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Quantity Selector (Minus, Value, Plus)
                    Row(
                        modifier = Modifier
                            .background(SurfaceContainerHigh, shape = RoundedCornerShape(8.dp))
                            .border(1.dp, WireframeOutlineVariant, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 4.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { if (quantity > 1) quantity-- },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "Decrease",
                                tint = WireframeOnBackground
                            )
                        }
                        Text(
                            text = quantity.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = WireframeOnBackground,
                            modifier = Modifier.width(32.dp),
                            textAlign = TextAlign.Center
                        )
                        IconButton(
                            onClick = { quantity++ },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Increase",
                                tint = WireframeOnBackground
                            )
                        }
                    }

                    // Add to Cart Button Block
                    Button(
                        onClick = {
                            onAddToCart(foodItem, quantity, selectedExtras.toList(), notes)
                            onNavigateToCart()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("add_to_cart_sum_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryWireframe),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Add to Cart",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "•",
                                fontSize = 14.sp,
                                color = OnPrimaryWireframe.copy(alpha = 0.7f)
                            )
                            Text(
                                text = String.format("$%.2f", finalPrice),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
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
                .verticalScroll(rememberScrollState())
        ) {
            // Large Top Image representation
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                WireframeImagePlaceholder(
                    modifier = Modifier.fillMaxSize(),
                    icon = Icons.Default.Fastfood,
                    contentDescription = "Large food detail layout"
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // Header Info Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = foodItem.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = WireframeOnBackground,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Stars",
                                tint = PrimaryWireframe,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "${foodItem.rating} (${foodItem.reviewsCount} reviews)",
                                fontSize = 14.sp,
                                color = OnSurfaceVariantWireframe,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Text(
                        text = String.format("$%.2f", basePrice),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryWireframe,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Divider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = WireframeOutlineVariant
                )

                // Description Block
                Text(
                    text = "Description",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = WireframeOnBackground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = foodItem.description,
                    fontSize = 14.sp,
                    color = OnSurfaceVariantWireframe,
                    lineHeight = 20.sp
                )

                // Extras Customizer Selection
                if (foodItem.extras.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Extras",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = WireframeOnBackground,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    foodItem.extras.forEach { extra ->
                        val isSelected = selectedExtras.contains(extra.name)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (isSelected) {
                                        selectedExtras.remove(extra.name)
                                    } else {
                                        selectedExtras.add(extra.name)
                                    }
                                }
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .border(1.dp, WireframeOutline, shape = RoundedCornerShape(4.dp))
                                        .background(
                                            if (isSelected) PrimaryWireframe else Color.Transparent,
                                            shape = RoundedCornerShape(4.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "checked",
                                            tint = OnPrimaryWireframe,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = extra.name,
                                    fontSize = 14.sp,
                                    color = WireframeOnBackground
                                )
                            }

                            Text(
                                text = String.format("+$%.2f", extra.price),
                                fontSize = 14.sp,
                                color = OnSurfaceVariantWireframe
                            )
                        }
                    }
                }

                // Custom instructions text input area
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Special Instructions",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = WireframeOnBackground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    placeholder = { Text("E.g., No onions, extra pickles", color = WireframeOutline) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryWireframe,
                        unfocusedBorderColor = WireframeOutlineVariant
                    )
                )
            }
        }
    }
}
