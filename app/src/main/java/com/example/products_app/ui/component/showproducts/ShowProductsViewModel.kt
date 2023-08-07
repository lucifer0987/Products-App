package com.example.products_app.ui.component.showproducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.products_app.data.DataRepository
import com.example.products_app.data.Resource
import com.example.products_app.data.dto.getProducts.GetProductsResponse
import com.example.products_app.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowProductsViewModel @Inject constructor(private val dataRepository: DataRepository) :
    BaseViewModel() {

    private val productDataPrivate = MutableLiveData<Resource<GetProductsResponse>>()
    val productData: LiveData<Resource<GetProductsResponse>> get() = productDataPrivate

    fun getProducts() {
        productDataPrivate.value = Resource.Loading()
        viewModelScope.launch {
            dataRepository.getProducts().collect {
                productDataPrivate.value = it
            }
        }
    }

}