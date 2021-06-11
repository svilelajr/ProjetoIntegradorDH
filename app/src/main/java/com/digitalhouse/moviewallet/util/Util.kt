package com.digitalhouse.moviewallet.util

import com.google.android.material.textfield.TextInputLayout

interface Util {

    fun validateEmail(layout: TextInputLayout):Boolean {
        return if (android.util.Patterns.EMAIL_ADDRESS.matcher(layout.editText?.text.toString()).matches()) {
            true
        }else{
            layout.error = "Email Invalido"
            false
        }
    }

    fun validadePassword(layout: TextInputLayout):Boolean{
        return if (layout.editText?.text.toString().isNotEmpty()){
            true
        }else {
            layout.error = "Senha Invalida"
            false
        }
    }


    fun validadeConfirmPassword(layoutPassword: TextInputLayout, layoutConfirmPassword: TextInputLayout): Boolean{
        return if (layoutPassword.editText?.text.toString() == layoutConfirmPassword.editText?.text.toString()){
            true
        }else{
            layoutConfirmPassword.error = "Senhas não correspondem"
            false
        }
    }

    fun validateName(layout: TextInputLayout): Boolean{
        return if (layout.editText?.text.toString().isNotEmpty()){
            true
        }else{
            layout.error = "Nome não pode estar vazio"
            false
        }
    }
}