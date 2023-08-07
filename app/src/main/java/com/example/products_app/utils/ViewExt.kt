package com.example.products_app.utils

import android.app.Activity
import android.app.Service
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.products_app.R
import com.google.android.material.bottomsheet.BottomSheetDialog

fun View.showKeyboard() {
    (this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
    (this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.getBottomSheetDialog(
    activity: Activity,
    layout: Int
): BottomSheetDialog {
    val dialog = BottomSheetDialog(context)
    val view = activity.layoutInflater.inflate(layout, null)
    dialog.setCancelable(true)
    dialog.setContentView(view)
    return dialog
}

fun View.showGenericPopup(
    activity: Activity,
    message: String,
    title: String? = null
) {
    val errorDialog = getBottomSheetDialog(
        activity, R.layout.bottom_error_dialog
    )
    errorDialog.setCancelable(false)
    errorDialog.setCanceledOnTouchOutside(false)

    val okButton = errorDialog.findViewById<AppCompatButton>(R.id.ok)
    val titleTextView = errorDialog.findViewById<TextView>(R.id.title)
    val messageTextView = errorDialog.findViewById<TextView>(R.id.message)

    if (title != null) titleTextView?.text = title
    messageTextView?.text = message
    okButton?.setOnClickListener {
        errorDialog.dismiss()
    }

    errorDialog.show()
}

fun View.showGenericToast(
    context: Context,
    message: String
) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun View.showEditTextError(
    errorMessage: String,
    editText: EditText,
    errorTextView: TextView
) {
    errorTextView.toVisible()
    errorTextView.text = errorMessage
    editText.background = ContextCompat.getDrawable(this.context, R.drawable.et_error_bg)

    editText.afterTextChanged {
        errorTextView.toGone()
        errorTextView.text = ""
        editText.background = ContextCompat.getDrawable(this.context, R.drawable.rounded_gray)
    }
}

fun View.resetEditTextError(
    editText: EditText,
    errorTextView: TextView
) {
    errorTextView.toGone()
    errorTextView.text = ""
    editText.background = ContextCompat.getDrawable(this.context, R.drawable.rounded_gray)
}
