package com.example.project_2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_2.IFetchProductUseCase
import com.example.project_2.db.AppDatabase
import com.example.project_2.dto.ProductListDto
import com.example.project_2.mapper.toEntity
import com.example.project_2.service.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private var productUseCase: IFetchProductUseCase,
    private val db: AppDatabase
) : ViewModel() {
    private val products = MutableLiveData<List<ProductListDto>>()
    val productsLiveData: LiveData<List<ProductListDto>>
        get() = products
    private val message = MutableLiveData<String?>()
    val messageLiveData: LiveData<String?>
        get() = message
    private val loading = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean>
        get() = loading

    fun fetchProducts() {
        loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = productUseCase.fetchProductsFromApi()) {
                is NetworkResource.Success -> {
                    val result = db.productDao().getAll()
                    response.data.forEach { product ->
                        product.isLiked = result.any { it.id == product.id }
                    }
                    products.postValue(response.data)
                }

                is NetworkResource.Error -> {
                    message.postValue(response.message)
                }
            }
            loading.postValue(false)
        }
    }

    fun heartClick(item: ProductListDto) {
        viewModelScope.launch(Dispatchers.IO) {
            if (item.isLiked) {
                db.productDao().insert(item.toEntity())
            } else {
                db.productDao().delete(item.toEntity())
            }
            if (item.isLiked) {
                message.postValue("Product added to Wishlist")
            } else {
                message.postValue("Product removed from Wishlist")
            }
        }
    }

}
