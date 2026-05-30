package com.example.data.repository

import com.example.data.local.dao.FoodDao
import com.example.data.local.entity.CartItem
import com.example.data.local.entity.OrderEntity
import com.example.data.model.FoodExtra
import com.example.data.model.FoodItem
import com.example.data.model.Restaurant
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class FoodRepository(private val foodDao: FoodDao) {

    // --- Static Full Catalog Data ---
    val restaurants = listOf(
        Restaurant(
            id = "burger_king",
            name = "Burger King",
            description = "American • Fast Food",
            cuisines = listOf("American", "Fast Food"),
            deliveryTime = "30-40 min",
            rating = 4.8,
            reviewsCount = "1.2k+",
            deliveryFee = 2.99,
            category = "Burger"
        ),
        Restaurant(
            id = "pizza_hut",
            name = "Pizza Hut",
            description = "Italian • Pizza",
            cuisines = listOf("Italian", "Pizza"),
            deliveryTime = "20-30 min",
            rating = 4.5,
            reviewsCount = "800+",
            deliveryFee = 3.99,
            category = "Pizza"
        ),
        Restaurant(
            id = "sushi_master",
            name = "Sushi Master",
            description = "Japanese • Sushi • Asian",
            cuisines = listOf("Japanese", "Sushi", "Asian"),
            deliveryTime = "35-45 min",
            rating = 4.6,
            reviewsCount = "120+",
            deliveryFee = 0.0, // Free Delivery
            category = "Sushi"
        ),
        Restaurant(
            id = "luigis_pizza",
            name = "Luigi's Pizza",
            description = "Italian • Pizza • Comfort Food",
            cuisines = listOf("Italian", "Pizza", "Comfort Food"),
            deliveryTime = "15-25 min",
            rating = 4.9,
            reviewsCount = "350+",
            deliveryFee = 1.49,
            category = "Pizza"
        ),
        Restaurant(
            id = "burger_joint",
            name = "Burger Joint",
            description = "American • Burgers • Fast Food",
            cuisines = listOf("American", "Burgers", "Fast Food"),
            deliveryTime = "20-30 min",
            rating = 4.8,
            reviewsCount = "250+",
            deliveryFee = 2.99,
            category = "Burger"
        )
    )

    private val cheeseburgerExtras = listOf(
        FoodExtra("cheese", "Extra Cheese", 1.50),
        FoodExtra("bacon", "Bacon", 2.00)
    )

    val foodItems = listOf(
        // Burger King
        FoodItem(
            id = "double_cheese_burger",
            name = "Double Cheese Burger",
            description = "Beef patty, double cheese, lettuce, tomato, special sauce.",
            price = 12.99,
            category = "Burger",
            rating = 4.8,
            reviewsCount = 1240,
            restaurantId = "burger_king",
            extras = cheeseburgerExtras
        ),
        FoodItem(
            id = "classic_cheeseburger",
            name = "Classic Cheeseburger",
            description = "A juicy beef patty topped with melted cheddar cheese, crisp lettuce, ripe tomatoes, onions, and our signature house sauce, all served on a toasted brioche bun.",
            price = 12.99,
            category = "Burger",
            rating = 4.8,
            reviewsCount = 124,
            restaurantId = "burger_king",
            extras = cheeseburgerExtras
        ),
        FoodItem(
            id = "large_fries",
            name = "Large Fries",
            description = "Crisp golden fries salted to perfection.",
            price = 4.99,
            category = "Burger",
            rating = 4.6,
            reviewsCount = 312,
            restaurantId = "burger_king"
        ),
        // Pizza Hut
        FoodItem(
            id = "margherita_pizza",
            name = "Margherita Pizza",
            description = "Classic tomato sauce, fresh mozzarella, basil.",
            price = 14.50,
            category = "Pizza",
            rating = 4.5,
            reviewsCount = 800,
            restaurantId = "pizza_hut"
        ),
        FoodItem(
            id = "pepperoni_pizza",
            name = "Pepperoni Pizza",
            description = "Classic pepperoni with thick mozzarella cheese and warm tomato crust.",
            price = 16.99,
            category = "Pizza",
            rating = 4.7,
            reviewsCount = 422,
            restaurantId = "pizza_hut"
        ),
        // Sushi Master
        FoodItem(
            id = "california_roll",
            name = "California Roll",
            description = "Crab meat, avocado, cucumber inside out roll with sesame seeds.",
            price = 11.99,
            category = "Sushi",
            rating = 4.6,
            reviewsCount = 120,
            restaurantId = "sushi_master"
        ),
        FoodItem(
            id = "salmon_nigiri",
            name = "Salmon Nigiri Set",
            description = "Fresh sliced salmon over pressed vinegar-seasoned rice.",
            price = 15.50,
            category = "Sushi",
            rating = 4.8,
            reviewsCount = 98,
            restaurantId = "sushi_master"
        ),
        // Luigi's Pizza
        FoodItem(
            id = "classic_veggie_pizza",
            name = "Classic Veggie Pizza",
            description = "Onions, bell peppers, sweet corn, mushrooms, olives and fresh basil.",
            price = 13.99,
            category = "Pizza",
            rating = 4.9,
            reviewsCount = 350,
            restaurantId = "luigis_pizza"
        ),
        // Drinks / Healthy
        FoodItem(
            id = "cold_brew_coffee",
            name = "Cold Brew Coffee",
            description = "Our premium dark roasted chill-filtered coffee.",
            price = 4.50,
            category = "Drinks",
            rating = 4.7,
            reviewsCount = 180,
            restaurantId = "burger_king"
        ),
        FoodItem(
            id = "fresh_orange_juice",
            name = "Fresh Orange Juice",
            description = "100% natural cold pressed sweet orange juice.",
            price = 3.99,
            category = "Drinks",
            rating = 4.8,
            reviewsCount = 210,
            restaurantId = "luigis_pizza"
        )
    )

    // --- Retrieval Helper Logic ---
    fun getRestaurantById(id: String): Restaurant? {
        return restaurants.find { it.id == id }
    }

    fun getFoodItemById(id: String): FoodItem? {
        return foodItems.find { it.id == id }
    }

    fun getMenuForRestaurant(restaurantId: String): List<FoodItem> {
        return foodItems.filter { it.restaurantId == restaurantId }
    }

    fun searchCatalog(query: String): List<FoodItem> {
        if (query.isBlank()) return foodItems
        return foodItems.filter {
            it.name.contains(query, ignoreCase = true) ||
            it.description.contains(query, ignoreCase = true)
        }
    }

    // --- DAO Bridge Operations (Persistent States) ----
    fun getCartItems(): Flow<List<CartItem>> = foodDao.getCartItems()

    suspend fun addCartItem(item: CartItem) {
        foodDao.insertCartItem(item)
    }

    suspend fun updateCartItem(item: CartItem) {
        foodDao.updateCartItem(item)
    }

    suspend fun deleteCartItem(item: CartItem) {
        foodDao.deleteCartItem(item)
    }

    suspend fun clearCart() {
        foodDao.clearCart()
    }

    fun getOrders(): Flow<List<OrderEntity>> = foodDao.getOrders()

    fun getOrderById(orderId: String): Flow<OrderEntity?> = foodDao.getOrderById(orderId)

    suspend fun placeOrder(order: OrderEntity) {
        foodDao.insertOrder(order)
    }

    suspend fun updateOrder(order: OrderEntity) {
        foodDao.updateOrder(order)
    }
}
