package com.example.project_2.service

import com.example.project_2.model.Product
import com.example.project_2.model.ProductModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("auth/products")
    suspend fun getProductsAll(): Response<ProductModel>

    @GET("auth/products/{id}")
    suspend fun getProductsById(
        @Path("id") id:Int
    ) : Product

}