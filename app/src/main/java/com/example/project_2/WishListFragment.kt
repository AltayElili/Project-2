package com.example.project_2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_2.databinding.FragmentWishListBinding
import com.matrix.android106_android.R
import com.matrix.android106_android.data.db.AppDatabase
import com.matrix.android106_android.databinding.FragmentWishListBinding
import com.matrix.android106_android.presentation.MyApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WishListFragment : Fragment() {

    private var _binding: FragmentWishListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WishListViewModel by viewModels()
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
        viewModel.getWishListData()

        viewModel.wishListLiveData.observe(viewLifecycleOwner) { products ->
            adapter.submitList(products)
        }

        viewModel.wishListStateLiveData.observe(viewLifecycleOwner) { isEmpty ->
            binding.emptyMessage.visibility = if (isEmpty) View.VISIBLE else View.GONE
            binding.emptyImage.visibility = if (isEmpty) View.VISIBLE else View.GONE
            binding.startShoppingButton.visibility = if (isEmpty) View.VISIBLE else View.GONE
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.startShoppingButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFavories_to_menuHome)
            Toast.makeText(requireContext(), "Start Shopping", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        adapter = WishlistAdapter { product,position ->
            viewModel.removeProductFromWishlist(product,position)
        }
        binding.wishListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.wishListRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
