package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.entity.CartItem
import com.example.data.local.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    // ---- Cart Items ----
    @Query("SELECT * FROM cart_items ORDER BY id ASC")
    fun getCartItems(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartItem)

    @Update
    suspend fun updateCartItem(item: CartItem)

    @Delete
    suspend fun deleteCartItem(item: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    // ---- Orders ----
    @Query("SELECT * FROM orders ORDER BY timestamp DESC")
    fun getOrders(): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE orderId = :orderId LIMIT 1")
    fun getOrderById(orderId: String): Flow<OrderEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Update
    suspend fun updateOrder(order: OrderEntity)
}
