package com.digitalhouse.moviewalletsprint1.utils

import com.google.android.material.textfield.TextInputLayout

interface Utils {

    fun validateEmail(layout: TextInputLayout):Boolean {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(layout.editText?.text.toString()).matches()) {
            return true
        }else{
        layout.error = "Email Invalido"
        return false
        }
    }

    fun validadePassword(layout: TextInputLayout):Boolean{
        if (layout.editText?.text.toString().isNotEmpty()){
            return true
        }else {
            layout.error = "Senha Invalida"
            return false
        }
    }


    fun validadeConfirmPassword(layoutPassword: TextInputLayout, layoutConfirmPassword: TextInputLayout): Boolean{
        if (layoutPassword.editText?.text.toString() == layoutConfirmPassword.editText?.text.toString()){
            return true
        }else{
            layoutConfirmPassword.error = "Senhas não correspondem"
            return false
        }
    }

    fun validateName(layout: TextInputLayout): Boolean{
        if (layout.editText?.text.toString().isNotEmpty()){
            return true
        }else{
            layout.error = "Nome não pode estar vazio"
            return false
        }


    }

}