package com.example.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FoodItem
import com.example.data.model.Restaurant
import com.example.ui.components.WireframeImagePlaceholder
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    restaurants: List<Restaurant>,
    popularFoods: List<FoodItem>,
    favoriteIds: Set<String>,
    cartItemCount: Int,
    selectedCategory: String,
    onCategoryClick: (String) -> Unit,
    onRestaurantClick: (String) -> Unit,
    onFoodClick: (String) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    onAddToCart: (FoodItem) -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToOrders: () -> Unit
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
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    Box(modifier = Modifier.padding(end = 4.dp)) {
                        IconButton(onClick = onNavigateToCart) {
                            Icon(imageVector = Icons.Default.ShoppingBag, contentDescription = "Cart")
                        }
                        if (cartItemCount > 0) {
                            Badge(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(x = (-4).dp, y = 4.dp),
                                containerColor = PrimaryWireframe
                            ) {
                                Text(
                                    text = cartItemCount.toString(),
                                    color = OnPrimaryWireframe,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
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
                    selected = true,
                    onClick = { /* Already Home */ },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryWireframe,
                        selectedTextColor = PrimaryWireframe,
                        indicatorColor = SurfaceContainerHigh
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToSearch,
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    label = { Text("Search", fontSize = 10.sp) }
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(WireframeBackground),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            // Delivering to Section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = SecondaryWireframe,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Delivering to",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = SecondaryWireframe
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.clickable { /* Prototyped address picker */ }
                    ) {
                        Text(
                            text = "123 Main Street, New York",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = WireframeOnBackground
                        )
                        Icon(
                            imageVector = Icons.Default.ExpandMore,
                            contentDescription = "Expand",
                            tint = PrimaryWireframe,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Search Bar Mock
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
                        .background(SurfaceContainerLowest, shape = RoundedCornerShape(8.dp))
                        .border(1.dp, WireframeOutlineVariant, shape = RoundedCornerShape(8.dp))
                        .clickable { onNavigateToSearch() }
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search icon",
                                tint = WireframeOutline,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Search for food or restaurants...",
                                fontSize = 14.sp,
                                color = WireframeOutline
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Divider(
                                modifier = Modifier
                                    .height(24.dp)
                                    .width(1.dp)
                                    .padding(horizontal = 8.dp),
                                color = WireframeOutlineVariant
                            )
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = "Filter option",
                                tint = PrimaryWireframe,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            // Categories horizontal slider
            item {
                val categories = listOf(
                    "Pizza" to Icons.Default.LocalPizza,
                    "Burger" to Icons.Default.LunchDining,
                    "Biryani" to Icons.Default.RiceBowl,
                    "Drinks" to Icons.Default.LocalCafe
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    items(categories) { pair ->
                        val category = pair.first
                        val icon = pair.second
                        val isSelected = selectedCategory.equals(category, ignoreCase = true)
                        Row(
                            modifier = Modifier
                                .background(
                                    color = if (isSelected) PrimaryWireframe else SurfaceContainerLow,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { onCategoryClick(category) }
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = category,
                                tint = if (isSelected) OnPrimaryWireframe else WireframeOnBackground,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = category,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isSelected) OnPrimaryWireframe else WireframeOnBackground
                            )
                        }
                    }
                }
            }

            // Featured Restaurants Horizontal Row
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "Featured Restaurants",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = WireframeOnBackground
                        )
                        Text(
                            text = "See all",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PrimaryWireframe,
                            modifier = Modifier.clickable { onNavigateToSearch() }
                        )
                    }

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(restaurants) { restaurant ->
                            val isFav = favoriteIds.contains(restaurant.id)
                            Column(
                                modifier = Modifier
                                    .width(240.dp)
                                    .clickable { onRestaurantClick(restaurant.id) }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                ) {
                                    WireframeImagePlaceholder(
                                        modifier = Modifier.fillMaxSize(),
                                        icon = Icons.Default.Storefront,
                                        contentDescription = "Restaurant image"
                                    )

                                    // Top right Heart Icon to toggle Favorite
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(8.dp)
                                            .background(SurfaceContainerLowest, shape = CircleShape)
                                            .border(1.dp, WireframeOutlineVariant, shape = CircleShape)
                                            .clickable { onFavoriteToggle(restaurant.id) }
                                            .padding(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                            contentDescription = "Fav heart",
                                            tint = if (isFav) Color.Red else WireframeOnBackground,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }

                                    // Delivery speed bottom left badge
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .padding(8.dp)
                                            .background(SurfaceContainerLowest, shape = RoundedCornerShape(4.dp))
                                            .border(1.dp, WireframeOutlineVariant, shape = RoundedCornerShape(4.dp))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = restaurant.deliveryTime,
                                            fontSize = 12.sp,
                                            color = WireframeOnBackground,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = restaurant.name,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = WireframeOnBackground
                                )
                                Text(
                                    text = restaurant.description,
                                    fontSize = 12.sp,
                                    color = SecondaryWireframe
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier.padding(top = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Star rating",
                                        tint = PrimaryWireframe,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = restaurant.rating.toString(),
                                        fontSize = 12.sp,
                                        color = WireframeOnBackground,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "(${restaurant.reviewsCount} ratings)",
                                        fontSize = 12.sp,
                                        color = SecondaryWireframe
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Popular Foods Vertical Section
            item {
                Text(
                    text = "Popular Foods",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = WireframeOnBackground,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
                )
            }

            val filteredPopularFoods = if (selectedCategory == "All") {
                popularFoods
            } else {
                popularFoods.filter { it.category.equals(selectedCategory, ignoreCase = true) }
            }

            if (filteredPopularFoods.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No popular food available for this category.",
                            color = OnSurfaceVariantWireframe,
                            fontSize = 14.sp
                        )
                    }
                }
            } else {
                items(filteredPopularFoods) { food ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .background(SurfaceContainerLowest, shape = RoundedCornerShape(12.dp))
                            .border(1.dp, WireframeOutlineVariant, shape = RoundedCornerShape(12.dp))
                            .clickable { onFoodClick(food.id) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WireframeImagePlaceholder(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            icon = Icons.Default.LunchDining,
                            contentDescription = "Food logo scale"
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = food.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = WireframeOnBackground
                            )
                            Text(
                                text = food.description,
                                fontSize = 12.sp,
                                color = SecondaryWireframe,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "$${food.price}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryWireframe
                                )

                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(SurfaceContainer, shape = CircleShape)
                                        .clickable { onAddToCart(food) }
                                        .padding(4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add to cart",
                                        tint = WireframeOnBackground,
                                        modifier = Modifier.size(18.dp)
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
