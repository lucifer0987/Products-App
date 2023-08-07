package com.example.products_app.data.remoteData

import com.example.products_app.data.Resource
import com.example.products_app.data.dto.addproducts.AddProductResponse
import com.example.products_app.data.dto.getProducts.GetProductsResponse
import com.example.products_app.data.error.ErrorCodes.DEFAULT_ERROR
import com.example.products_app.data.error.ErrorCodes.NETWORK_ERROR
import com.example.products_app.data.error.ErrorCodes.NO_INTERNET_CONNECTION
import com.example.products_app.utils.NetworkConnectivity
import okhttp3.RequestBody
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteData @Inject constructor(
    private val serviceGenerator: ServiceGenerator,
    private val networkConnectivity: NetworkConnectivity
) : RemoteDataSource {

    override suspend fun addProduct(
        productName: RequestBody,
        productPrice: RequestBody,
        productTax: RequestBody,
        productType: RequestBody
    ): Resource<AddProductResponse> {
        val addProductService = serviceGenerator.createService(ApiService::class.java)
        return when (val response = processCall {
            addProductService.addProduct(
                productName,
                productPrice,
                productTax,
                productType
            )
        }) {
            is AddProductResponse -> {
                Resource.Success(data = response)
            }

            else -> {
                Resource.DataError(error = response)
            }
        }
    }

    override suspend fun getProducts(): Resource<GetProductsResponse> {
        val getProductService = serviceGenerator.createService(ApiService::class.java)
        return when (val response = processCall {
            getProductService.getProducts()
        }) {
            is GetProductsResponse -> {
                Resource.Success(data = response)
            }

            else -> {
                Resource.DataError(error = response)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            if (response.isSuccessful) {
                response.body()
            } else {
                DEFAULT_ERROR
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}