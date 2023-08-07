package com.example.products_app.utils

import android.view.View

object Utilities {

    fun hideLoader(mainLayout: View, loaderLayout: View) {
        mainLayout.toVisible()
        loaderLayout.toGone()
    }

    fun hideLoaderFailure(mainLayout: View, loaderLayout: View) {
        mainLayout.toGone()
        loaderLayout.toGone()
    }

    fun showLoader(mainLayout: View, loaderLayout: View) {
        mainLayout.toGone()
        loaderLayout.toVisible()
    }
}
