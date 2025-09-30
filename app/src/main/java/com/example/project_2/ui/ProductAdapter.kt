package com.example.project_2.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_2.databinding.ItemProductBinding
import com.example.project_2.db.ProductEntity
import com.example.project_2.dto.ProductListDto
import com.example.project_2.R


class ProductAdapter(
    private var products: List<ProductListDto>,
    private val itemClick: (item: ProductListDto) -> Unit,
    private val heartClick: (item: ProductListDto) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.imgLike.setOnClickListener {
                val product = products[adapterPosition]
                product.isLiked = !product.isLiked
                heartClick(product)
                notifyItemChanged(adapterPosition)
            }
            binding.root.setOnClickListener {
                val product = products[adapterPosition]
                itemClick(product)
            }
        }

        fun bind(product: ProductListDto, context: Context) {
            Glide
                .with(binding.root.context)
                .load(product.thumbnail)
                .into(binding.imageView)
            binding.txtProductName.text = product.title
            binding.txtPrice.text = context.getString(R.string.txt_price, product.price.toString())
            binding.imgLike.setImageResource(
                if (product.isLiked) R.mipmap.red_heart else R.mipmap.empty_heart
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position], holder.itemView.context)
    }

    fun updateProducts(newProducts: List<ProductListDto>) {
        products = newProducts
        notifyDataSetChanged()
    }

    fun updateFavorites(favorites: List<ProductEntity>) {

        products = products.map { product ->
            product.copy(isLiked = favorites.any { it.id == product.id })
        }
        notifyDataSetChanged()
    }
}
