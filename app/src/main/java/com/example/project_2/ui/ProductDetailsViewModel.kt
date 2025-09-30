package com.example.project_2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_2.model.Product
import com.example.project_2.service.ProductService
import com.example.project_2.service.RetrofitInstance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor
    ( private val api: Retrofit): ViewModel() {

    private val productDetails = MutableLiveData<Product>()
    val productDetailsLiveData: LiveData<Product>
        get() = productDetails
    private val loading = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean>
        get() = loading
    private val errorMessage = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String>
        get() = errorMessage

    fun getProductById(productId: Int) {
        loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val productService = RetrofitInstance.api.create(ProductService::class.java)
                val response = productService.getProductsById(productId)
                productDetails.postValue(response)
            } catch (e: Exception) {
                errorMessage.postValue("Failed to fetch product details: ${e.message}")
            } finally {
                loading.postValue(false)
            }
        }
    }
}