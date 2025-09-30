package com.example.project_2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_2.db.ProductDao
import com.example.project_2.db.ProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val productDao: ProductDao
) : ViewModel() {

    private val wishList = MutableLiveData<List<ProductEntity>>()
    val wishListLiveData: LiveData<List<ProductEntity>>
        get() = wishList
    private val wishListState = MutableLiveData<Boolean>()
    val wishListStateLiveData: LiveData<Boolean>
        get() = wishListState

    fun getWishListData() {
        viewModelScope.launch(Dispatchers.IO) {
            val resultList = productDao.getAll()
            withContext(Dispatchers.Main) {
                wishList.postValue(resultList)
                wishListState.postValue(resultList.isEmpty())
            }
        }
    }

    fun removeProductFromWishlist(product: ProductEntity, position : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.delete(product)
            withContext(Dispatchers.Main) {
                wishList.value = wishList.value?.toMutableList()?.apply {
                    removeAt(position)
                }
            }
        }
    }
}