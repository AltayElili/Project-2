package com.example.project_2.repository

import com.example.project_2.dto.ProductListDto
import com.example.project_2.service.NetworkResource

interface IProductRepository {
    suspend fun fetchProductsFromApi(): NetworkResource<List<ProductListDto>>
}