package com.example.project_2.remoteDataSource

import com.example.project_2.service.NetworkResource
import com.example.project_2.dto.ProductListDto
import com.example.project_2.service.ProductService
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val productService: ProductService
) : IProductRemoteDataSource {

    override suspend fun fetchProducts(): NetworkResource<List<ProductListDto>> {
        return try {
            val response = productService.getProductsAll()
            if (response.isSuccessful && response.body() != null) {
                val productList = response.body()?.products ?: emptyList()
                NetworkResource.Success(productList)
            } else {
                NetworkResource.Error("Error fetching products")
            }
        } catch (e: Exception) {
            NetworkResource.Error(e.message ?: "Unknown Error")
        }
    }
}