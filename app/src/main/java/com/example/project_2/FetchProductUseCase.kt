package com.example.project_2

import com.example.project_2.dto.ProductListDto
import com.example.project_2.repository.IProductRepository
import com.example.project_2.service.NetworkResource
import javax.inject.Inject

class FetchProductUseCase @Inject constructor(
    private val productRepository: IProductRepository
):IFetchProductUseCase {

    override suspend fun fetchProductsFromApi(): NetworkResource<List<ProductListDto>> {
        return productRepository.fetchProductsFromApi()
    }
}