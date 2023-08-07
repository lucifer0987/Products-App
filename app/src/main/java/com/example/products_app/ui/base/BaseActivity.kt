package com.example.products_app.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.products_app.utils.SharedPreferences

abstract class BaseActivity : AppCompatActivity() {
    abstract fun observeViewModel()
    protected abstract fun initViewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SharedPreferences.init(applicationContext);
        initViewBinding()
        observeViewModel()
    }

}
