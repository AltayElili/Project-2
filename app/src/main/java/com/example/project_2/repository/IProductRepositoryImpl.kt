package com.example.project_2.repository

import com.example.project_2.dto.ProductListDto
import com.example.project_2.remoteDataSource.IProductRemoteDataSource
import com.example.project_2.service.NetworkResource
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productRemoteDataSource: IProductRemoteDataSource
) : BaseRepository(), IProductRepository {

    override suspend fun fetchProductsFromApi(): NetworkResource<List<ProductListDto>> {
        return productRemoteDataSource.fetchProducts()
    }
}