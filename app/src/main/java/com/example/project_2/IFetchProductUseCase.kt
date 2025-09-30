package com.example.project_2

import com.example.project_2.dto.ProductListDto
import com.example.project_2.service.NetworkResource

interface IFetchProductUseCase {
    suspend fun fetchProductsFromApi(): NetworkResource<List<ProductListDto>>
}