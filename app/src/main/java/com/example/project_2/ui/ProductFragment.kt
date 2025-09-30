package com.example.project_2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.project_2.db.AppDatabase
import com.example.project_2.dto.ProductListDto
import com.example.project_2.mapper.toEntity
import com.example.project_2.mapper.toListDto
import com.example.project_2.service.ProductService
import com.example.project_2.service.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.project_2.R
import com.example.project_2.databinding.FragmentProductBinding
import kotlinx.coroutines.withContext


class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.btnFab.setOnClickListener {
            findNavController().navigate(R.id.action_menuHome_to_addProductFragment)
        }

        fetchProducts()
    }

    override fun onResume() {
        super.onResume()
        refreshFavorites()
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(
            products = emptyList(),
            heartClick = { item -> heartClick(item) },
            itemClick = { selectedProduct ->
                val bundle = Bundle().apply { putSerializable("product", selectedProduct) }
                findNavController().navigate(
                    R.id.action_menuHome_to_productIdFragment,
                    bundle
                )
            }
        )
        binding.rcyProduct.apply {
            adapter = productAdapter
            setHasFixedSize(true)
        }
    }

    private fun fetchProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            val productService = RetrofitInstance.api.create(ProductService::class.java)
            val response = productService.getProductsAll()
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    val productList = body.products.map { it.toListDto() }

                    val favoriteProducts = AppDatabase.getInstance(requireContext())
                        .productDao()
                        .getAll()


                    val updatedList = productList.map { product ->
                        product.copy(
                            isLiked = favoriteProducts.any { it.id == product.id }
                        )
                    }

                    withContext(Dispatchers.Main) {
                        productAdapter.updateProducts(updatedList)
                    }
                }
            } else {
                Log.e(
                    "Myerror",
                    "Get Products Error:${response.message()} ${response.errorBody()}"
                )
            }
        }
    }

    private fun refreshFavorites() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val favoriteProducts = AppDatabase.getInstance(requireContext()).productDao().getAll()
            withContext(Dispatchers.Main) {
                productAdapter.updateFavorites(favoriteProducts)
            }
        }
    }

    private fun heartClick(item: ProductListDto) {
        val db = AppDatabase.getInstance(requireContext())
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            if (item.isLiked) db.productDao().insert(item.toEntity())
            else db.productDao().delete(item.toEntity())

            withContext(Dispatchers.Main) {
                Toast.makeText(
                    requireContext(),
                    if (item.isLiked) "Product added to Wishlist" else "Product removed from Wishlist",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

