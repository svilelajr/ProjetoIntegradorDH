package com.digitalhouse.moviewalletsprint1.activities

import android.content.ContentProvider
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.digitalhouse.moviewalletsprint1.R
import com.google.android.material.textfield.TextInputLayout

class RegisterScreen : AppCompatActivity() {
    private val textField by lazy { findViewById<TextInputLayout>(R.id.til_genre_register) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)

        val items = listOf("Masculino", "Feminino")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        (textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
}