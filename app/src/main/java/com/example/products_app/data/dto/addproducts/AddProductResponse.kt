package com.example.products_app.data.dto.addproducts

import com.google.gson.annotations.SerializedName

data class AddProductResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("product_details")
    val productDetails: ProductDetails? = null,
    @SerializedName("product_id")
    val productId: Int? = null,
    @SerializedName("success")
    val success: Boolean? = null
) {
    data class ProductDetails(
        @SerializedName("image")
        val image: String? = null,
        @SerializedName("price")
        val price: Double? = null,
        @SerializedName("product_name")
        val productName: String? = null,
        @SerializedName("product_type")
        val productType: String? = null,
        @SerializedName("tax")
        val tax: Double? = null
    )
}