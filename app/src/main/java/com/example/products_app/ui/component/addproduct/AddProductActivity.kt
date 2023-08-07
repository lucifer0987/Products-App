package com.example.products_app.ui.component.addproduct

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.products_app.R
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
import com.example.products_app.databinding.ActivityAddProductBinding
import com.example.products_app.errors.ErrorManager
import com.example.products_app.ui.base.BaseActivity
import com.example.products_app.utils.ProductTypes
import com.example.products_app.utils.Utilities.hideLoader
import com.example.products_app.utils.Utilities.showLoader
import com.example.products_app.utils.hideKeyboard
import com.example.products_app.utils.observe
import com.example.products_app.utils.resetEditTextError
import com.example.products_app.utils.showEditTextError
import com.example.products_app.utils.showGenericPopup
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddProductActivity : BaseActivity() {

    @Inject
    lateinit var errorManager: ErrorManager
    private val addProductViewModel: AddProductViewModel by viewModels()
    private lateinit var binding: ActivityAddProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()
        initListeners()
        initProductTypes()
    }

    override fun initViewBinding() {
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun observeViewModel() {
        observe(addProductViewModel.errorData, ::handleEdittextValidation)
        observe(addProductViewModel.addProductLiveData, ::handleAddProductResult)
        observeEditTextError(addProductViewModel.editTextErrorCode)
    }

    private fun initToolbar() {
        binding.toolbarLayout.textScreenTitle.text = getString(R.string.add_product)
        binding.toolbarLayout.imageBackButton.setOnClickListener { finish() }
    }

    private fun initListeners() {
        binding.addProductBtn.setOnClickListener { addProduct() }
    }

    private fun handleAddProductResult(status: Resource<AddProductResponse>) {
        when (status) {
            is Resource.Loading -> {
                showLoader(binding.mainArea, binding.loaderArea)
            }

            is Resource.Success -> {
                status.data?.let { addResponse ->
                    if (addResponse.success == true) {
                        hideLoader(binding.mainArea, binding.loaderArea)
                        binding.root.showGenericPopup(
                            this@AddProductActivity,
                            this.resources.getString(R.string.product_add_success),
                            this.resources.getString(R.string.success)
                        )
                        resetEditTextValues()
                    } else {
                        hideLoader(binding.mainArea, binding.loaderArea)
                        binding.root.showGenericPopup(
                            this@AddProductActivity,
                            this.resources.getString(R.string.product_add_failure)
                        )
                    }
                }
            }

            is Resource.DataError -> {
                val error = errorManager.getError(status.error.toString().toInt())
                hideLoader(binding.mainArea, binding.loaderArea)
                binding.root.showGenericPopup(
                    this@AddProductActivity,
                    error.description
                )
            }

            else -> {
                hideLoader(binding.mainArea, binding.loaderArea)
                binding.root.showGenericPopup(
                    this@AddProductActivity,
                    this.resources.getString(R.string.default_error)
                )
            }
        }
    }

    private fun addProduct() {
        binding.root.hideKeyboard()
        addProductViewModel.checkData(
            binding.productName.text.trim().toString(),
            binding.productPrice.text.trim().toString(),
            binding.productTax.text.trim().toString(),
            binding.productType.text.trim().toString(),
        )
    }

    private fun handleEdittextValidation(status: Resource<String>) {
        when (status) {
            is Resource.Loading -> {
                showLoader(binding.mainArea, binding.loaderArea)
            }

            is Resource.MultipleDataError -> {
                hideLoader(binding.mainArea, binding.loaderArea)
                status.errors?.forEach() {
                    val errorCode = it.split(ERROR_SEPARATOR).first().toInt()
                    addProductViewModel.showEditTextError(errorCode)
                }
            }

            is Resource.Success -> {
                resetEditTextErrors()
                addProductViewModel.addProduct(
                    binding.productName.text.trim().toString(),
                    binding.productPrice.text.trim().toString(),
                    binding.productTax.text.trim().toString(),
                    binding.productType.text.trim().toString()
                )
            }

            else -> {
                hideLoader(binding.mainArea, binding.loaderArea)
            }
        }

    }

    private fun observeEditTextError(code: LiveData<String>) {
        code.observe(this, Observer { error ->
            val errorCode = error.split(ERROR_SEPARATOR).first()
            val errorMessage = error.split(ERROR_SEPARATOR).last()
            when (errorCode.toInt()) {
                NAME_REQUIRED -> {
                    binding.root.showEditTextError(
                        errorMessage, binding.productName, binding.productNameRequiredTv
                    )
                }

                PRICE_REQUIRED -> {
                    binding.root.showEditTextError(
                        errorMessage, binding.productPrice, binding.productPriceRequiredTv
                    )
                }

                NOT_ZERO_PRICE_VALUE -> {
                    binding.root.showEditTextError(
                        errorMessage, binding.productPrice, binding.productPriceRequiredTv
                    )
                }

                TAX_REQUIRED -> {
                    binding.root.showEditTextError(
                        errorMessage, binding.productTax, binding.productTaxRequiredTv
                    )
                }

                NOT_ZERO_TAX_VALUE -> {
                    binding.root.showEditTextError(
                        errorMessage, binding.productTax, binding.productTaxRequiredTv
                    )
                }

                TYPE_REQUIRED -> {
                    binding.root.showEditTextError(
                        errorMessage, binding.productType, binding.productTypeRequiredTv
                    )
                }

                else -> {
                    resetEditTextErrors()
                }
            }
        })
    }

    private fun resetEditTextErrors() {
        binding.root.resetEditTextError(binding.productName, binding.productNameRequiredTv)
        binding.root.resetEditTextError(binding.productPrice, binding.productPriceRequiredTv)
        binding.root.resetEditTextError(binding.productTax, binding.productTaxRequiredTv)
        binding.root.resetEditTextError(binding.productType, binding.productTypeRequiredTv)
    }

    private fun resetEditTextValues() {
        binding.productName.text.clear()
        binding.productPrice.text.clear()
        binding.productTax.text.clear()
        binding.productType.setText(binding.productType.adapter.getItem(0).toString(), false)
    }

    private fun initProductTypes() {
        val productTypes = arrayListOf<String>(
            ProductTypes.PRODUCT_TYPE.value, ProductTypes.IN_STOCK.value,
            ProductTypes.OUT_OF_STOCK.value, ProductTypes.NO_INFORMATION.value
        )

        binding.productType.setAdapter(
            ArrayAdapter(this, android.R.layout.simple_list_item_1, productTypes)
        )

        binding.productType.threshold = Integer.MAX_VALUE
        binding.productType.setText(productTypes.first(), false)
        binding.productType.inputType = 0

        binding.productType.setOnTouchListener { _, _ ->
            binding.productType.showDropDown()
            false
        }
    }
}