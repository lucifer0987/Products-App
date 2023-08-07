package com.example.products_app.data

import com.example.products_app.data.dto.addproducts.AddProductResponse
import com.example.products_app.data.dto.getProducts.GetProductsResponse
import com.example.products_app.data.remoteData.RemoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DataRepository @Inject constructor(
    private val remoteRepository: RemoteData,
    private val ioDispatcher: CoroutineContext
) : DataRepositorySource {

    override suspend fun addProduct(
        productName: RequestBody,
        productPrice: RequestBody,
        productTax: RequestBody,
        productType: RequestBody
    ): Flow<Resource<AddProductResponse>> {
        return flow {
            emit(
                remoteRepository.addProduct(
                    productName,
                    productPrice,
                    productTax,
                    productType
                )
            )
        }.flowOn(ioDispatcher)
    }

    override suspend fun getProducts(): Flow<Resource<GetProductsResponse>> {
        return flow {
            emit(
                remoteRepository.getProducts()
            )
        }.flowOn(ioDispatcher)
    }

}
