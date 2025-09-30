package com.example.project_2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_2.databinding.FragmentWishListBinding
import com.example.project_2.db.AppDatabase
import com.example.project_2.db.ProductEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WishListFragment : Fragment() {

    private var _binding: FragmentWishListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: WishlistAdapter
    private val appDatabase by lazy { AppDatabase.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentWishListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRecyclerView()
        getWishListData()


    }

    private fun setupRecyclerView() {
        adapter = WishlistAdapter { product,position ->
            removeProductFromWishlist(product,position)
        }
        binding.wishListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.wishListRecyclerView.adapter = adapter
    }
    private fun getWishListData(){
        CoroutineScope(Dispatchers.IO).launch {
            val resultList= appDatabase.productDao().getAll()
            withContext(Dispatchers.Main){
                adapter.submitList(resultList)
                binding.emptyStateText.visibility =
                    if (resultList.isEmpty()) View.VISIBLE else View.GONE
            }
        }

    }

    private fun removeProductFromWishlist(product: ProductEntity,position:Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            appDatabase.productDao().delete(product)
            withContext(Dispatchers.Main){
                adapter.deleteItem(product, position)
                Toast.makeText(requireContext(), "${product.name} removed from wishlist", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
