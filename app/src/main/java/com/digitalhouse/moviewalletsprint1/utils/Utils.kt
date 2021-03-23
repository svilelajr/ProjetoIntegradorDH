package com.digitalhouse.moviewalletsprint1.utils

import com.google.android.material.textfield.TextInputLayout

interface Utils {

    fun validateEmail(layout: TextInputLayout) {
        when {
            layout.editText?.text.isNullOrBlank() -> {
                layout.error = "Obrigatório"
            }
            else ->
                layout.error = null
        }
    }
}