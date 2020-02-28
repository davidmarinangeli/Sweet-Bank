package com.davidm.payees.utils

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


fun isFormValid(map: Map<TextInputLayout, TextInputEditText>): Boolean {
    var valid = true
    map.forEach {
        if (it.key.error.isNullOrEmpty()) {
            it.key.isErrorEnabled = false
        } else {
            valid = false
        }
    }
    return valid
}

fun searchAndSetErrorsOnForm(map: Map<TextInputLayout, TextInputEditText>) {
    map.map { (layout, edittext) ->
        if (edittext.text.toString().isBlank()) {
            // foreach required element in the form that is blank, we show an error message
            layout.error = "The field is required"
        } else {
            // if the required element is filled, we set the error to null
            layout.error = null
        }
    }
}
