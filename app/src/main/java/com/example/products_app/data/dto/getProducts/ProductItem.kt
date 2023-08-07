package com.example.products_app.data.dto.getProducts

data class ProductItem(
    var title: String? = null,
    var price: Double? = null,
    var tax: Double? = null,
    var image: String? = null,
    var type: String? = null
)