package com.example.project_2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.project_2.databinding.FragmentProductIdBinding
import com.example.project_2.model.Product


class ProductIdFragment : Fragment() {
    private lateinit var binding:FragmentProductIdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentProductIdBinding.inflate(inflater,container,false)
        val product= arguments?.getSerializable("product") as? Product
        product?.let{
            Glide
                .with(requireContext())
                .load(it.thumbnail)
                .into(binding.imgProductDetail)
            binding.txtProductTitle.text = it.title
            binding.txtProductPrice.text = "Price: $${it.price}"
        }
        return binding.root
    }
}
