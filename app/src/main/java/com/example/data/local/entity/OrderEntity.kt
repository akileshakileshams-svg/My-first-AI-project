package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val orderId: String, // e.g. "FD-8924"
    val restaurantName: String,
    val status: String, // "CONFIRMED", "PREPARING", "OUT_FOR_DELIVERY", "DELIVERED"
    val timestamp: Long,
    val deliveryAddress: String,
    val itemsSummary: String, // e.g. "2 Items • Burger Joint"
    val subtotal: Double,
    val deliveryFee: Double,
    val taxes: Double,
    val total: Double
)
