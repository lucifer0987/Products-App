package com.example.products_app.data.error.mapper

import android.content.Context
import com.example.products_app.R
import com.example.products_app.data.error.ErrorCodes.DEFAULT_ERROR
import com.example.products_app.data.error.ErrorCodes.NAME_REQUIRED
import com.example.products_app.data.error.ErrorCodes.NETWORK_ERROR
import com.example.products_app.data.error.ErrorCodes.NOT_ZERO_PRICE_VALUE
import com.example.products_app.data.error.ErrorCodes.NOT_ZERO_TAX_VALUE
import com.example.products_app.data.error.ErrorCodes.NO_INTERNET_CONNECTION
import com.example.products_app.data.error.ErrorCodes.PRICE_REQUIRED
import com.example.products_app.data.error.ErrorCodes.TAX_REQUIRED
import com.example.products_app.data.error.ErrorCodes.TYPE_REQUIRED
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ErrorMapper @Inject constructor(@ApplicationContext val context: Context) :
    ErrorMapperSource {

    override fun getErrorString(errorId: Int): String {
        return context.getString(errorId)
    }

    override val errorsMap: Map<Int, String>
        get() = mapOf(
            Pair(DEFAULT_ERROR, getErrorString(R.string.default_error)),
            Pair(NO_INTERNET_CONNECTION, getErrorString(R.string.no_internet)),
            Pair(NETWORK_ERROR, getErrorString(R.string.network_error)),
            Pair(NAME_REQUIRED, getErrorString(R.string.required_field)),
            Pair(PRICE_REQUIRED, getErrorString(R.string.required_field)),
            Pair(TAX_REQUIRED, getErrorString(R.string.required_field)),
            Pair(TYPE_REQUIRED, getErrorString(R.string.required_field)),
            Pair(NOT_ZERO_PRICE_VALUE, getErrorString(R.string.not_zero)),
            Pair(NOT_ZERO_TAX_VALUE, getErrorString(R.string.not_zero))
        ).withDefault { getErrorString(R.string.network_error) }
}
