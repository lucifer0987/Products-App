package com.example.products_app.data

import com.example.products_app.data.dto.addproducts.AddProductResponse
import com.example.products_app.data.dto.getProducts.GetProductsResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface DataRepositorySource {

    suspend fun addProduct(
        productName: RequestBody,
        productPrice: RequestBody,
        productTax: RequestBody,
        productType: RequestBody
    ): Flow<Resource<AddProductResponse>>

    suspend fun getProducts(): Flow<Resource<GetProductsResponse>>

}
