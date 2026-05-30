package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.FoodViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                val foodViewModel: FoodViewModel = viewModel()

                val cartItems by foodViewModel.cartItems.collectAsState()
                val subtotal by foodViewModel.subtotal.collectAsState()
                val deliveryFee by foodViewModel.deliveryFee.collectAsState()
                val taxesAndFees by foodViewModel.taxesAndFees.collectAsState()
                val total by foodViewModel.total.collectAsState()
                val favoriteIds by foodViewModel.favoriteRestaurantIds.collectAsState()
                val searchQuery by foodViewModel.searchQuery.collectAsState()
                val selectedCategory by foodViewModel.selectedCategory.collectAsState()
                val searchResults by foodViewModel.searchResults.collectAsState()
                val orders by foodViewModel.orders.collectAsState()

                NavHost(
                    navController = navController,
                    startDestination = "splash",
                    modifier = Modifier.fillMaxSize()
                ) {
                    // 1. Splash / Get Started Screen
                    composable("splash") {
                        SplashScreen(
                            onGetStarted = {
                                navController.navigate("login") {
                                    popUpTo("splash") { inclusive = true }
                                }
                            }
                        )
                    }

                    // 2. Authentication Login Screen
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = {
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // 3. Main Dashboard Screen
                    composable("home") {
                        HomeScreen(
                            restaurants = foodViewModel.restaurants,
                            popularFoods = foodViewModel.foodItems,
                            favoriteIds = favoriteIds,
                            cartItemCount = cartItems.sumOf { it.quantity },
                            selectedCategory = selectedCategory,
                            onCategoryClick = { foodViewModel.selectCategory(it) },
                            onRestaurantClick = {
                                // Default to search or click action
                                navController.navigate("search")
                            },
                            onFoodClick = { foodId ->
                                navController.navigate("details/$foodId")
                            },
                            onFavoriteToggle = { restaurantId ->
                                foodViewModel.toggleFavorite(restaurantId)
                            },
                            onAddToCart = { food ->
                                foodViewModel.addToCart(food, 1, emptyList())
                            },
                            onNavigateToCart = {
                                navController.navigate("cart")
                            },
                            onNavigateToSearch = {
                                navController.navigate("search")
                            },
                            onNavigateToOrders = {
                                val latestOrderId = orders.firstOrNull()?.orderId
                                if (latestOrderId != null) {
                                    foodViewModel.selectTrackingOrder(latestOrderId)
                                    navController.navigate("tracking/$latestOrderId")
                                } else {
                                    // Fallback to custom tracking representation
                                    navController.navigate("tracking/FD-8924")
                                }
                            }
                        )
                    }

                    // 4. Searching and filtering screen
                    composable("search") {
                        SearchScreen(
                            searchResults = searchResults,
                            restaurants = foodViewModel.restaurants,
                            searchQuery = searchQuery,
                            onQueryChange = { foodViewModel.updateQuery(it) },
                            selectedCategory = selectedCategory,
                            onCategoryClick = { foodViewModel.selectCategory(it) },
                            onNavigateToHome = {
                                navController.navigate("home") {
                                    popUpTo("home") { inclusive = false }
                                }
                            },
                            onNavigateToOrders = {
                                val latestOrderId = orders.firstOrNull()?.orderId
                                if (latestOrderId != null) {
                                    foodViewModel.selectTrackingOrder(latestOrderId)
                                    navController.navigate("tracking/$latestOrderId")
                                } else {
                                    navController.navigate("tracking/FD-8924")
                                }
                            },
                            onFoodClick = { foodId ->
                                navController.navigate("details/$foodId")
                            },
                            onNavigateToCart = {
                                navController.navigate("cart")
                            }
                        )
                    }

                    // 5. Food item detail and customizing screen
                    composable(
                        route = "details/{foodId}",
                        arguments = listOf(navArgument("foodId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val foodId = backStackEntry.arguments?.getString("foodId")
                        val foodItem = foodViewModel.getFoodItem(foodId)
                        val rId = foodItem?.restaurantId ?: "burger_king"
                        val restaurant = foodViewModel.restaurants.find { it.id == rId }

                        DetailsScreen(
                            foodItem = foodItem,
                            restaurantName = restaurant?.name ?: "Burger joint",
                            isFavorite = favoriteIds.contains(rId),
                            onFavoriteToggle = { foodViewModel.toggleFavorite(rId) },
                            onBackClick = { navController.popBackStack() },
                            onAddToCart = { food, qty, extras, notes ->
                                foodViewModel.addToCart(food, qty, extras, notes)
                            },
                            onNavigateToCart = {
                                navController.navigate("cart")
                            }
                        )
                    }

                    // 6. Shopping Cart screen
                    composable("cart") {
                        CartScreen(
                            cartItems = cartItems,
                            subtotal = subtotal,
                            deliveryFee = deliveryFee,
                            taxesAndFees = taxesAndFees,
                            total = total,
                            onBackClick = { navController.popBackStack() },
                            onNavigateToSearch = { navController.navigate("search") },
                            onIncrement = { foodViewModel.incrementCartQuantity(it) },
                            onDecrement = { foodViewModel.decrementCartQuantity(it) },
                            onNavigateToCheckout = { navController.navigate("checkout") }
                        )
                    }

                    // 7. Order Checkout payment screen
                    composable("checkout") {
                        CheckoutScreen(
                            currentSubtotal = subtotal,
                            currentDelivery = deliveryFee,
                            currentTaxes = taxesAndFees,
                            currentTotal = total,
                            onBackClick = { navController.popBackStack() },
                            onPlaceOrder = { method, address ->
                                foodViewModel.placeOrder(method, address)
                            },
                            onNavigateToTracking = {
                                // Placing order triggers setting VM active order tracking.
                                val latestId = foodViewModel.trackingOrderId.value ?: "FD-8924"
                                navController.navigate("tracking/$latestId") {
                                    popUpTo("home") { inclusive = false }
                                }
                            }
                        )
                    }

                    // 8. Order tracking screen (Active status timeline)
                    composable(
                        route = "tracking/{orderId}",
                        arguments = listOf(navArgument("orderId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val orderId = backStackEntry.arguments?.getString("orderId")
                        val activeOrder by foodViewModel.trackingOrder.collectAsState()

                        TrackingScreen(
                            order = activeOrder,
                            onNavigateToHome = {
                                navController.navigate("home") {
                                    popUpTo("home") { inclusive = true }
                                }
                            },
                            onNavigateToSearch = {
                                navController.navigate("search")
                            },
                            onNavigateToOrders = {
                                // Already in active order state
                            },
                            onNavigateToCart = {
                                navController.navigate("cart")
                            }
                        )
                    }
                }
            }
        }
    }
}
