package com.example.products_app.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object SharedPreferences {
    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) sharedPreferences =
            context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
    }

    fun readString(key: String?, defValue: String?): String? {
        return sharedPreferences!!.getString(key, defValue)
    }

    fun writeString(key: String?, value: String?) {
        val prefsEditor = sharedPreferences!!.edit()
        prefsEditor.putString(key, value)
        prefsEditor.apply()
    }

    fun readBoolean(key: String?, defValue: Boolean): Boolean {
        return sharedPreferences!!.getBoolean(key, defValue)
    }

    fun writeBoolean(key: String?, value: Boolean) {
        val prefsEditor = sharedPreferences!!.edit()
        prefsEditor.putBoolean(key, value)
        prefsEditor.apply()
    }

    fun readInt(key: String?, defValue: Int): Int {
        return sharedPreferences!!.getInt(key, defValue)
    }

    fun writeInt(key: String?, value: Int?) {
        val prefsEditor = sharedPreferences!!.edit()
        prefsEditor.putInt(key, value!!).apply()
    }

    fun clearAll() {
        val prefsEditor = sharedPreferences!!.edit().clear()
        prefsEditor.apply()
    }
}
