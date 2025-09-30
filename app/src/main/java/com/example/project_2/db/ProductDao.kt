package com.example.project_2.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {

    @Query("SELECT * FROM ProductEntity")
    suspend fun getAll(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: ProductEntity): Long

    @Delete
    suspend fun delete(product: ProductEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM ProductEntity WHERE id = :productId)")
    fun isProductInWishlist(productId: Int): Boolean
}
