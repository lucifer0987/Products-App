package com.example.products_app.data.error

import com.example.products_app.data.error.ErrorCodes.DEFAULT_ERROR
import com.example.products_app.errors.ErrorManager

class Error(val code: Int, val description: String) {
    constructor(exception: Exception) : this(
        code = DEFAULT_ERROR, description = exception.message ?: ""
    )
}

object ErrorCodes {
    const val DEFAULT_ERROR = -1
    const val NO_INTERNET_CONNECTION = -2
    const val NETWORK_ERROR = -3
    const val NAME_REQUIRED = -4
    const val PRICE_REQUIRED = -5
    const val TAX_REQUIRED = -6
    const val TYPE_REQUIRED = -7
    const val NOT_ZERO_PRICE_VALUE = -8
    const val NOT_ZERO_TAX_VALUE = -9
}

object ErrorUtils {
    const val ERROR_SEPARATOR = "#"

    fun getErrorMessage(error: Any?, errorManager: ErrorManager): String {
        if (error.toString().toIntOrNull() != null) {
            return errorManager.getError(error.toString().toInt()).description
        } else if (error is String) {
            return if (error.contains(ERROR_SEPARATOR)) errorManager.getError(
                error.split(
                    ERROR_SEPARATOR
                ).first().toInt()
            ).description
            else error
        }
        return ""
    }
}
