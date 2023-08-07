package com.example.products_app.ui.component.showproducts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.products_app.data.dto.getProducts.ProductItem
import com.example.products_app.databinding.ProductItemBinding

class ShowProductsAdapter(
    private var productList: MutableList<ProductItem>
) : RecyclerView.Adapter<ShowProductsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowProductsViewHolder {
        val viewBinding = ProductItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ShowProductsViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ShowProductsViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    fun getData(): MutableList<ProductItem>{
        return productList
    }

    fun setData(productList: MutableList<ProductItem>){
        this.productList = productList
        notifyDataSetChanged()
    }

}