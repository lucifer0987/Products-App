package com.example.products_app.ui.component.showproducts.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.products_app.R
import com.example.products_app.data.dto.getProducts.ProductItem
import com.example.products_app.databinding.ProductItemBinding
import com.squareup.picasso.Picasso

class ShowProductsViewHolder(private val itemBinding: ProductItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(productItem: ProductItem) {
        itemBinding.productNameDescText.text = "${productItem.title} \u2022 ${productItem.type}"
        itemBinding.priceTxt.text = productItem.price.toString()
        itemBinding.taxTxt.text = productItem.tax.toString()

        if (productItem.image != "") {
            Picasso.get().load(productItem.image).error(R.drawable.no_image)
                .placeholder(R.drawable.no_image).into(itemBinding.productImage)
        }

    }
}