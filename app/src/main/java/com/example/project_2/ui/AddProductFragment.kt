package com.example.project_2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.project_2.databinding.FragmentAddProductBinding
import com.example.project_2.model.ProductRequest
import com.example.project_2.service.ProductService
import com.example.project_2.service.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class AddProductFragment : Fragment() {

    private lateinit var binding: FragmentAddProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProductBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveProduct.setOnClickListener {
            val title = binding.edtTitle.text.toString()
            val price = binding.edtPrice.text.toString()

            if (title.isNotEmpty() && price.isNotEmpty()) {
                val newProduct = ProductRequest(title, price.toDouble())
                addProduct(newProduct)

            } else {
                binding.edtTitle.error = "Title is required"
                binding.edtPrice.error = "Price is required"
            }
        }
    }

    private fun addProduct(product : ProductRequest) {
        val newProduct = ProductRequest("Teze Ürün", 19.99)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val productService = RetrofitInstance.api.create(ProductService::class.java)
                val response = productService.addProduct(newProduct)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.code() == 201) {
                        Toast.makeText(
                            requireContext(),
                            "Mehsul ugurla elave edildi",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Mehsul elave edilerken problem yarandi.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "MyError: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}
