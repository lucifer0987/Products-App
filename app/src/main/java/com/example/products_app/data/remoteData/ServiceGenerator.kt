package com.example.products_app.data.remoteData

import com.example.products_app.APIConstants.CONNECT_TIMEOUT
import com.example.products_app.APIConstants.READ_TIMEOUT
import com.example.products_app.App
import com.example.products_app.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceGenerator @Inject constructor() {
    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private val retrofit: Retrofit

    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            return loggingInterceptor
        }

    init {
        okHttpBuilder.addInterceptor(logger)
        okHttpBuilder.connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        val client = okHttpBuilder.build()
        retrofit = Retrofit.Builder()
            .baseUrl(App.appContext.resources.getString(R.string.base_url)).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}
