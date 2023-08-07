package com.example.products_app.ui.component.showproducts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.products_app.R
import com.example.products_app.data.Resource
import com.example.products_app.data.dto.getProducts.GetProductsResponse
import com.example.products_app.data.dto.getProducts.ProductItem
import com.example.products_app.databinding.ActivityShowProductsBinding
import com.example.products_app.errors.ErrorManager
import com.example.products_app.ui.base.BaseActivity
import com.example.products_app.ui.component.addproduct.AddProductActivity
import com.example.products_app.ui.component.showproducts.adapter.ShowProductsAdapter
import com.example.products_app.utils.Utilities.hideLoader
import com.example.products_app.utils.Utilities.showLoader
import com.example.products_app.utils.afterTextChanged
import com.example.products_app.utils.observe
import com.example.products_app.utils.resetEditTextError
import com.example.products_app.utils.showGenericPopup
import com.example.products_app.utils.toGone
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShowProductsActivity : BaseActivity() {

    @Inject
    lateinit var errorManager: ErrorManager
    private val showProductsViewModel: ShowProductsViewModel by viewModels()
    private lateinit var binding: ActivityShowProductsBinding
    private lateinit var showProductsAdapter: ShowProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()
        initListeners()
        showProductsViewModel.getProducts()
    }

    override fun initViewBinding() {
        binding = ActivityShowProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun observeViewModel() {
        observe(showProductsViewModel.productData, ::handleProductResult)
    }

    private fun initToolbar() {
        binding.toolbarLayout.textScreenTitle.text = getString(R.string.show_product)
        binding.toolbarLayout.imageBackButton.toGone()
    }

    private fun initListeners() {
        binding.addProductArea.setOnClickListener { goToAddProductScreen() }
        binding.searchEt.afterTextChanged { query ->
            Log.e("query", query.toString())
            if(query.isNotEmpty()){
                filterListByQuery(query)
            } else {
                showProductsViewModel.productData.value?.let {
                    handleProductResult(it)
                }
            }
        }
    }

    private fun filterListByQuery(query: String) {
        val newProductList = mutableListOf<ProductItem>()
        val productList: MutableList<ProductItem>? = showProductsViewModel.productData.value?.let { getProductItemList(it) }

        productList?.let { products ->
            for(product in products){
                if(product.title?.lowercase()?.contains(query.lowercase()) == true){
                    newProductList.add(product)
                }
            }

            if(this::showProductsAdapter.isInitialized) {
                showProductsAdapter.setData(newProductList)
            } else {
                bindProductRv(newProductList)
            }
        } ?: {
            binding.mainArea.toGone()
            binding.root.showGenericPopup(this@ShowProductsActivity, this@ShowProductsActivity.resources.getString(R.string.default_error))
        }

    }

    override fun onResume() {
        super.onResume()
    }

    private fun handleProductResult(status: Resource<GetProductsResponse>) {
        when (status) {
            is Resource.Loading -> {
                showLoader(binding.mainArea, binding.loaderArea)
            }

            is Resource.Success -> {
                val productItemList = getProductItemList(status)
                hideLoader(binding.mainArea, binding.loaderArea)
                bindProductRv(productItemList)
            }

            is Resource.DataError -> {
                val error = errorManager.getError(status.error.toString().toInt())
                hideLoader(binding.mainArea, binding.loaderArea)
                binding.root.showGenericPopup(
                    this@ShowProductsActivity,
                    error.description
                )
            }

            else -> {
                hideLoader(binding.mainArea, binding.loaderArea)
                binding.root.showGenericPopup(
                    this@ShowProductsActivity,
                    this.resources.getString(R.string.default_error)
                )
            }
        }
    }

    private fun getProductItemList(status: Resource<GetProductsResponse>): MutableList<ProductItem> {
        val productItemList = mutableListOf<ProductItem>()

        status.data?.let { productList ->
            productList.forEach { product ->
                val currProduct = ProductItem()
                currProduct.title = product.productName
                currProduct.type = product.productType
                currProduct.price = product.price
                currProduct.tax = product.tax
                currProduct.image = product.image
                productItemList.add(currProduct)
            }
        }

        return productItemList
    }

    private fun bindProductRv(productItemList: MutableList<ProductItem>) {
        val layoutManager = GridLayoutManager(this, 2)
        binding.productsRv.layoutManager = layoutManager
        showProductsAdapter = ShowProductsAdapter(productItemList)
        binding.productsRv.adapter = showProductsAdapter
    }

    private fun goToAddProductScreen() {
        startActivity(Intent(this@ShowProductsActivity, AddProductActivity::class.java))
    }
}