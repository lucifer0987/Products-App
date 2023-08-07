package com.example.products_app.ui.base

import androidx.lifecycle.ViewModel
import com.example.products_app.errors.ErrorManager
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var errorManager: ErrorManager
}
