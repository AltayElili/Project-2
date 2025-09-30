package com.example.project_2.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ProductEntity")
data class ProductEntity(
    @PrimaryKey val id:Int,
    val name:String,
    val price:String,
    val thumbnail:String
)