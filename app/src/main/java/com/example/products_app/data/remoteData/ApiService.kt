package com.example.products_app.data.remoteData

import com.example.products_app.data.dto.addproducts.AddProductResponse
import com.example.products_app.data.dto.getProducts.GetProductsResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

private const val acceptHeader = "Accept: application/vnd.api+json"

interface ApiService {

    @Multipart
    @POST("add")
    suspend fun addProduct(
        @Part("product_name") productName: RequestBody,
        @Part("price") productPrice: RequestBody,
        @Part("tax") productTax: RequestBody,
        @Part("product_type") productType: RequestBody,
    ): Response<AddProductResponse>

    @Headers(acceptHeader)
    @GET("get")
    suspend fun getProducts(): Response<GetProductsResponse>

}