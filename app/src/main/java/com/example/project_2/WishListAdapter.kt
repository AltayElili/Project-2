package com.example.project_2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_2.db.ProductEntity

class WishListAdapter(
    private val onRemoveClick: (ProductEntity, Int) -> Unit
) : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    private val items = mutableListOf<ProductEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val binding =
            ItemWish.inflate(LayoutInflater.from(parent.context), parent, false)
        return WishlistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<ProductEntity>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun deleteItem(product: ProductEntity, position: Int) {
        items.remove(product)
        notifyItemRemoved(position)
    }

    inner class WishlistViewHolder(private val binding: ItemWishlistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductEntity) {
            Glide
                .with(binding.root.context)
                .load(product.thumbnail)
                .into(binding.imageView2)
            binding.txtProductName2.text = product.name
            binding.txtPrice2.text = product.price
            binding.heartButton2.setOnCheckedChangeListener(null)
            binding.heartButton2.isChecked = true
            binding.heartButton2.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    onRemoveClick(product, adapterPosition)
                }
            }
        }
    }
}
