package com.digitalhouse.moviewalletsprint1.activities

import android.content.ContentProvider
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.digitalhouse.moviewalletsprint1.R
import com.digitalhouse.moviewalletsprint1.utils.Utils
import com.google.android.material.textfield.TextInputLayout

class RegisterScreen : AppCompatActivity(),Utils {
    private val fieldNameLayout by lazy { findViewById<TextInputLayout>(R.id.til_name_register) }
    private val fieldEmailLayout by lazy { findViewById<TextInputLayout>(R.id.til_email_register) }
    private val fieldPasswordLayout by lazy { findViewById<TextInputLayout>(R.id.til_password_register) }
    private val fieldConfirmPasswordLayout by lazy {findViewById<TextInputLayout>(R.id.til_confirmpassword_register) }
    private val textField by lazy { findViewById<TextInputLayout>(R.id.til_genre_register) }
    private val btnConfirm by lazy { findViewById<Button>(R.id.confirm_button)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)

        val items = listOf("Masculino", "Feminino")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        (textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        initClick()
    }

    private fun initClick() {
//
        btnConfirm.setOnClickListener{
            if(validateName(fieldNameLayout) && validateEmail(fieldEmailLayout) && validadePassword(fieldPasswordLayout) && validadeConfirmPassword(fieldPasswordLayout,fieldConfirmPasswordLayout)){
                val intent = Intent(this,HomeScreen::class.java)
                startActivity(intent)
            }
        }
    }
}