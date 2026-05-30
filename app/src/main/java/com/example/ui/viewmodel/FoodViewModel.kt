package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.database.AppDatabase
import com.example.data.local.entity.CartItem
import com.example.data.local.entity.OrderEntity
import com.example.data.model.FoodItem
import com.example.data.model.Restaurant
import com.example.data.repository.FoodRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

class FoodViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = FoodRepository(database.foodDao())

    // --- Catalog and Search States ---
    val restaurants: List<Restaurant> = repository.restaurants
    val foodItems: List<FoodItem> = repository.foodItems

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // --- Screen State Selection ---
    private val _selectedFoodId = MutableStateFlow<String?>(null)
    val selectedFoodId: StateFlow<String?> = _selectedFoodId.asStateFlow()

    // --- Favorites State (in-memory) ---
    private val _favoriteRestaurantIds = MutableStateFlow<Set<String>>(setOf("burger_king"))
    val favoriteRestaurantIds: StateFlow<Set<String>> = _favoriteRestaurantIds.asStateFlow()

    // --- Search Results (reactive) ---
    val searchResults: StateFlow<List<FoodItem>> = combine(_searchQuery, _selectedCategory) { query, category ->
        var list = if (query.isBlank()) {
            repository.foodItems
        } else {
            repository.searchCatalog(query)
        }
        if (category != "All") {
            list = list.filter { it.category.equals(category, ignoreCase = true) }
        }
        list
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- Cart State (backed by Room DB) ---
    val cartItems: StateFlow<List<CartItem>> = repository.getCartItems()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- Cost calculations (derived from cart items) ---
    val subtotal: StateFlow<Double> = cartItems.map { items ->
        items.sumOf { it.price * it.quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val deliveryFee: StateFlow<Double> = cartItems.map { items ->
        if (items.isEmpty()) return@map 0.0
        // Burger Joint delivery fee is 2.99, let's keep a stable standard fee for now or base it on first item
        3.99
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 3.99)

    val taxesAndFees: StateFlow<Double> = cartItems.map { items ->
        if (items.isEmpty()) return@map 0.0
        2.50
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 2.50)

    val total: StateFlow<Double> = combine(subtotal, deliveryFee, taxesAndFees) { sub, delivery, taxes ->
        if (sub == 0.0) 0.0 else sub + delivery + taxes
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    // --- Orders Records (backed by Room DB) ---
    val orders: StateFlow<List<OrderEntity>> = repository.getOrders()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- Selected Order Tracker ---
    private val _trackingOrderId = MutableStateFlow<String?>(null)
    val trackingOrderId: StateFlow<String?> = _trackingOrderId.asStateFlow()

    val trackingOrder: StateFlow<OrderEntity?> = _trackingOrderId.flatMapLatest { orderId ->
        if (orderId == null) flowOf(null)
        else repository.getOrderById(orderId)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // --- Search Helper Actions ---
    fun updateQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    // --- Favorites logic ---
    fun toggleFavorite(restaurantId: String) {
        val currentSet = _favoriteRestaurantIds.value
        if (currentSet.contains(restaurantId)) {
            _favoriteRestaurantIds.value = currentSet - restaurantId
        } else {
            _favoriteRestaurantIds.value = currentSet + restaurantId
        }
    }

    // --- Cart Actions ---
    fun addToCart(foodItem: FoodItem, quantity: Int, extras: List<String>, customNotes: String = "") {
        viewModelScope.launch {
            val existing = cartItems.value.find { it.name == foodItem.name && it.extras == extras.joinToString() }
            if (existing != null) {
                repository.updateCartItem(existing.copy(quantity = existing.quantity + quantity))
            } else {
                val priceWithExtras = foodItem.price + (if (extras.contains("Extra Cheese")) 1.50 else 0.0) + (if (extras.contains("Bacon")) 2.00 else 0.0)
                val newItem = CartItem(
                    name = foodItem.name,
                    price = priceWithExtras,
                    quantity = quantity,
                    notes = customNotes,
                    extras = extras.joinToString(", ")
                )
                repository.addCartItem(newItem)
            }
        }
    }

    fun incrementCartQuantity(item: CartItem) {
        viewModelScope.launch {
            repository.updateCartItem(item.copy(quantity = item.quantity + 1))
        }
    }

    fun decrementCartQuantity(item: CartItem) {
        viewModelScope.launch {
            if (item.quantity <= 1) {
                repository.deleteCartItem(item)
            } else {
                repository.updateCartItem(item.copy(quantity = item.quantity - 1))
            }
        }
    }

    fun placeOrder(paymentMethod: String, address: String) {
        val currentCart = cartItems.value
        val currentTotal = total.value
        val currentSubtotal = subtotal.value
        val currentDelivery = deliveryFee.value
        val currentTaxes = taxesAndFees.value

        if (currentCart.isEmpty()) return

        val orderId = "FD-${UUID.randomUUID().toString().take(4).uppercase()}"
        val itemsSummary = "${currentCart.sumOf { it.quantity }} Items • ${currentCart.firstOrNull()?.name ?: "Burger Joint"}"

        val order = OrderEntity(
            orderId = orderId,
            restaurantName = if (currentCart.any { it.name.contains("Burger", ignoreCase = true) }) "Burger Joint" else "Luigi's Pizza",
            status = "CONFIRMED", // Start state
            timestamp = System.currentTimeMillis(),
            deliveryAddress = address,
            itemsSummary = itemsSummary,
            subtotal = currentSubtotal,
            deliveryFee = currentDelivery,
            taxes = currentTaxes,
            total = currentTotal
        )

        viewModelScope.launch {
            // Place in local DB
            repository.placeOrder(order)
            // Empty the cart
            repository.clearCart()
            // Track this order immediately
            _trackingOrderId.value = orderId

            // Simulate delivery tracking changes
            launch {
                // Wait 10 seconds to change state to PREPARING
                delay(10000)
                repository.updateOrder(order.copy(status = "PREPARING"))

                // Wait 15 seconds to change state to OUT_FOR_DELIVERY
                delay(15000)
                repository.updateOrder(order.copy(status = "OUT_FOR_DELIVERY"))

                // Wait 20 seconds to change state to DELIVERED
                delay(20000)
                repository.updateOrder(order.copy(status = "DELIVERED"))
            }
        }
    }

    fun selectTrackingOrder(orderId: String) {
        _trackingOrderId.value = orderId
    }

    // --- Helper function to query food details directly ---
    fun getFoodItem(id: String?): FoodItem? {
        return id?.let { repository.getFoodItemById(it) }
    }
}
