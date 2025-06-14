package com.k5.omsetku.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.k5.omsetku.model.Product
import java.text.NumberFormat
import java.util.Locale
import androidx.core.graphics.toColorInt
import com.k5.omsetku.databinding.ChooseProductListBinding
import com.k5.omsetku.model.Category

class ChooseProductListAdapter(
    private val onItemClick: (Product) -> Unit
): RecyclerView.Adapter<ChooseProductListAdapter.ProductViewHolder>(), Filterable {

    var productList: List<Product> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            this.filteredProducts = value
            notifyDataSetChanged()
        }
    var categoryList: List<Category> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val selectedProductIds = mutableSetOf<String>()
    private var filteredProducts: List<Product> = productList


    val getSelectedProducts: List<Product> get() {
        return productList.filter { selectedProductIds.contains(it.productId) }
    }

    inner class ProductViewHolder(val binding: ChooseProductListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            val rupiahFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

            binding.displayProductName.text = product.productName
            binding.displayProductPrice.text = rupiahFormat.format(product.productPrice)
            binding.displayProductCategory.text = categoryList.find{ it.categoryId == product.categoryId }?.categoryName
            binding.displayProductStock.text = product.productStock.toString()

            if (selectedProductIds.contains(product.productId)) {
                binding.itemContainer.setCardBackgroundColor("#E5E5E5".toColorInt())
            } else {
                binding.itemContainer.setCardBackgroundColor("#FFFFFF".toColorInt())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ChooseProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = filteredProducts[position]

        holder.bind(currentProduct)

        holder.itemView.setOnClickListener { onItemClick(currentProduct) }
    }

    // Method untuk mengelola item yang terpilih berdasarkan ID item
    fun toggleSelection(item: Product) {
        if (selectedProductIds.contains(item.productId)) {
            selectedProductIds.remove(item.productId)
        } else {
            selectedProductIds.add(item.productId)
        }

        val currentPosition = filteredProducts.indexOfFirst { it.productId == item.productId }
        if (currentPosition != -1) {
            notifyItemChanged(currentPosition)
        }
    }

    fun getCategories(): List<Category> {
        return categoryList
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearSelections() {
        selectedProductIds.clear()
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filteredProducts = if (charSearch.isEmpty()) {
                    productList
                } else {
                    val resultList = mutableListOf<Product>()
                    for (row in productList) { // Filter dari list asli
                        if (row.productName.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT)) ||
                            categoryList.find{ it.categoryId == row.categoryId }?.categoryName?.lowercase(Locale.ROOT)
                                ?.contains(charSearch.lowercase(Locale.ROOT)) == true
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredProducts
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredProducts = results?.values as List<Product>

                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount() = filteredProducts.size
}

