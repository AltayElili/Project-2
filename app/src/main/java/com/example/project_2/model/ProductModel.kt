package com.example.project_2.model

import com.example.project_2.dto.ProductListDto

data class ProductModel(
    val limit: Int,
    val products: List<ProductListDto>,
    val skip: Int,
    val total: Int
)