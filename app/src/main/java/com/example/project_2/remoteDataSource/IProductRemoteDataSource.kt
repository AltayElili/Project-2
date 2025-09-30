package com.example.project_2.remoteDataSource

import com.example.project_2.service.NetworkResource
import com.example.project_2.dto.ProductListDto

interface IProductRemoteDataSource {
    suspend fun fetchProducts(): NetworkResource<List<ProductListDto>>
}