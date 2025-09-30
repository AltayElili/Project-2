package com.example.project_2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.project_2.ui.ProductDetailsViewModel
import com.example.project_2.databinding.FragmentProductIdBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private val viewModel: ProductDetailsViewModel by viewModels()
    private lateinit var binding: FragmentProductIdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductIdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId = arguments?.getInt("productId") ?: -1
        if (productId != -1) {
            viewModel.getProductById(productId)
        }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.productDetailsLiveData.observe(viewLifecycleOwner) { product ->
            product?.let {
                Glide.with(requireContext())
                    .load(it.thumbnail)
                    .into(binding.imgProductDetail)
                binding.txtProductTitle.text = it.title
                binding.txtProductPrice.text = "Price: $${it.price}"
            }
        }
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}