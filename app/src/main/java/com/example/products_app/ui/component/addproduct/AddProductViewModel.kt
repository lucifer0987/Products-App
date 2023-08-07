package com.example.products_app.ui.component.addproduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.products_app.data.DataRepository
import com.example.products_app.data.Resource
import com.example.products_app.data.dto.addproducts.AddProductResponse
import com.example.products_app.data.error.ErrorCodes
import com.example.products_app.data.error.ErrorCodes.NAME_REQUIRED
import com.example.products_app.data.error.ErrorCodes.NOT_ZERO_PRICE_VALUE
import com.example.products_app.data.error.ErrorCodes.NOT_ZERO_TAX_VALUE
import com.example.products_app.data.error.ErrorCodes.PRICE_REQUIRED
import com.example.products_app.data.error.ErrorCodes.TAX_REQUIRED
import com.example.products_app.data.error.ErrorCodes.TYPE_REQUIRED
import com.example.products_app.data.error.ErrorUtils.ERROR_SEPARATOR
import com.example.products_app.ui.base.BaseViewModel
import com.example.products_app.utils.ProductTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val dataRepository: DataRepository) :
    BaseViewModel() {

    private val errorDataPrivate = MutableLiveData<Resource<String>>()
    val errorData: LiveData<Resource<String>> get() = errorDataPrivate

    private val editTextErrorCodePrivate = MutableLiveData<String>()
    val editTextErrorCode: LiveData<String> get() = editTextErrorCodePrivate

    private val addProductLiveDataPrivate = MutableLiveData<Resource<AddProductResponse>>()
    val addProductLiveData: LiveData<Resource<AddProductResponse>> get() = addProductLiveDataPrivate

    fun checkData(name: String, price: String, tax: String, type: String) {
        errorDataPrivate.value = Resource.Loading()
        val errorList = ArrayList<String>()

        if (name.isEmpty()) {
            errorList.add(NAME_REQUIRED.toString())
        }
        if (price.isEmpty()) {
            errorList.add(PRICE_REQUIRED.toString())
        }else{
            if(price.toDouble() == 0.0){
                errorList.add(NOT_ZERO_PRICE_VALUE.toString())
            }
        }
        if (tax.isEmpty()) {
            errorList.add(TAX_REQUIRED.toString())
        }else{
            if(tax.toDouble() == 0.0){
                errorList.add(NOT_ZERO_TAX_VALUE.toString())
            }
        }
        if (type == ProductTypes.PRODUCT_TYPE.value) {
            errorList.add(TYPE_REQUIRED.toString())
        }

        if (errorList.isEmpty()) {
            errorDataPrivate.value = Resource.Success()
        } else {
            errorDataPrivate.value = Resource.MultipleDataError(errorList)
        }
    }

    fun showEditTextError(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        editTextErrorCodePrivate.value = error.code.toString() + ERROR_SEPARATOR + error.description
    }

    fun addProduct(name: String, price: String, tax: String, type: String) {
        addProductLiveDataPrivate.value = Resource.Loading()

        viewModelScope.launch {
            val productName: RequestBody =
                RequestBody.create("text/plain".toMediaTypeOrNull(), name)
            val productPrice: RequestBody =
                RequestBody.create("text/plain".toMediaTypeOrNull(), price)
            val productTax: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), tax)
            val productType: RequestBody =
                RequestBody.create("text/plain".toMediaTypeOrNull(), type)

            dataRepository.addProduct(productName, productPrice, productTax, productType).collect {
                addProductLiveDataPrivate.value = it
            }

        }
    }
}