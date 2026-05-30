package com.example.data.model

data class FoodItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: String, // "Pizza", "Burger", "Biryani", "Drinks", "Sushi", "Healthy", "Vegan"
    val rating: Double,
    val reviewsCount: Int,
    val restaurantId: String,
    val extras: List<FoodExtra> = emptyList()
)

data class FoodExtra(
    val id: String,
    val name: String,
    val price: Double
)

data class Restaurant(
    val id: String,
    val name: String,
    val description: String, // "American • Fast Food"
    val cuisines: List<String>,
    val deliveryTime: String, // "30-40 min"
    val rating: Double,
    val reviewsCount: String, // "1.2k+"
    val deliveryFee: Double,
    val category: String // "Pizza", "Burger", "Biryani", "Sushi", "Drinks"
)
