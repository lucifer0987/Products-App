package com.example.products_app.errors

import com.example.products_app.data.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
