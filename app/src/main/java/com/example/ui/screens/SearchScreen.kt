package com.example.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FoodItem
import com.example.data.model.Restaurant
import com.example.ui.components.WireframeImagePlaceholder
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchResults: List<FoodItem>,
    restaurants: List<Restaurant>,
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    selectedCategory: String,
    onCategoryClick: (String) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToOrders: () -> Unit,
    onFoodClick: (String) -> Unit,
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
                    selected = true,
                    onClick = { /* Already Search */ },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    label = { Text("Search", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryWireframe,
                        selectedTextColor = PrimaryWireframe,
                        indicatorColor = SurfaceContainerHigh
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToOrders,
                    icon = { Icon(Icons.Default.Assignment, contentDescription = "Orders") },
                    label = { Text("Orders", fontSize = 10.sp) }
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
        ) {
            // Search Input Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onQueryChange,
                    placeholder = { Text("Search restaurants or dishes", color = WireframeOutline) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search icon",
                            tint = WireframeOutline
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .testTag("search_field_input"),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceContainerLowest,
                        unfocusedContainerColor = SurfaceContainerLowest,
                        focusedBorderColor = PrimaryWireframe,
                        unfocusedBorderColor = WireframeOutlineVariant
                    ),
                    singleLine = true
                )

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(52.dp)
                        .border(1.dp, WireframeOutlineVariant, shape = RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLowest, shape = RoundedCornerShape(8.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = "Filter menu",
                        tint = PrimaryWireframe
                    )
                }
            }

            // Categories horizontal slider selection
            val categoryTabs = listOf("All", "Pizza", "Sushi", "Burgers", "Healthy", "Vegan")
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(categoryTabs) { category ->
                    val isSelected = selectedCategory.equals(category, ignoreCase = true)
                    Text(
                        text = category,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isSelected) OnPrimaryWireframe else WireframeOnBackground,
                        modifier = Modifier
                            .background(
                                color = if (isSelected) PrimaryWireframe else SurfaceContainerHigh,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onCategoryClick(category) }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }

            // Restaurant / Dishear results list
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                item {
                    Text(
                        text = "Popular Near You",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = WireframeOnBackground,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
                    )
                }

                // Gather list of restaurants matching search category or matches
                val matchedRestaurants = if (selectedCategory == "All") {
                    restaurants
                } else {
                    restaurants.filter {
                        it.category.equals(selectedCategory, ignoreCase = true) ||
                        (selectedCategory == "Burgers" && it.category == "Burger")
                    }
                }

                if (matchedRestaurants.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No restaurants match your filters.",
                                color = OnSurfaceVariantWireframe
                            )
                        }
                    }
                } else {
                    items(matchedRestaurants) { restaurant ->
                        // Gather dishes from result matching
                        val associatedDish = searchResults.firstOrNull { it.restaurantId == restaurant.id }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clickable {
                                    // Navigate to details of associated dish if exists, else fallback to king cheeseburger
                                    onFoodClick(associatedDish?.id ?: "classic_cheeseburger")
                                },
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, WireframeOutlineVariant),
                            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest)
                        ) {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(130.dp)
                                ) {
                                    WireframeImagePlaceholder(
                                        modifier = Modifier.fillMaxSize(),
                                        icon = Icons.Default.Fastfood,
                                        contentDescription = "Search result"
                                    )

                                    // Delivery time badge in bottom right corner
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .padding(8.dp)
                                            .background(SurfaceContainerLowest, shape = RoundedCornerShape(4.dp))
                                            .border(1.dp, WireframeOutlineVariant, shape = RoundedCornerShape(4.dp))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = restaurant.deliveryTime,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = WireframeOnBackground
                                        )
                                    }
                                }

                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = restaurant.name,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = WireframeOnBackground
                                        )

                                        Row(
                                            modifier = Modifier
                                                .background(SurfaceContainerHigh, shape = RoundedCornerShape(4.dp))
                                                .padding(horizontal = 6.dp, vertical = 2.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Star,
                                                contentDescription = "star",
                                                tint = PrimaryWireframe,
                                                modifier = Modifier.size(12.dp)
                                            )
                                            Text(
                                                text = restaurant.rating.toString(),
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = WireframeOnBackground
                                            )
                                        }
                                    }

                                    Text(
                                        text = restaurant.description,
                                        fontSize = 12.sp,
                                        color = OnSurfaceVariantWireframe,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        modifier = Modifier.padding(top = 8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.DirectionsBike,
                                            contentDescription = "delivery",
                                            tint = OnSurfaceVariantWireframe,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Text(
                                            text = if (restaurant.deliveryFee == 0.0) "Free Delivery" else "$${restaurant.deliveryFee} Delivery",
                                            fontSize = 12.sp,
                                            color = OnSurfaceVariantWireframe
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
