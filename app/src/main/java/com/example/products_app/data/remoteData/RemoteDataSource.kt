package com.example.products_app.data.remoteData

import com.example.products_app.data.Resource
import com.example.products_app.data.dto.addproducts.AddProductResponse
import com.example.products_app.data.dto.getProducts.GetProductsResponse
import okhttp3.RequestBody

interface RemoteDataSource {

    suspend fun addProduct(
        productName: RequestBody,
        productPrice: RequestBody,
        productTax: RequestBody,
        productType: RequestBody,
    ): Resource<AddProductResponse>

    suspend fun getProducts(): Resource<GetProductsResponse>

}