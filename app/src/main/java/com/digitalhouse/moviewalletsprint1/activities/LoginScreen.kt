package com.digitalhouse.moviewalletsprint1.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.digitalhouse.moviewalletsprint1.R
import com.digitalhouse.moviewalletsprint1.utils.Utils
import com.google.android.material.textfield.TextInputLayout

class LoginScreen: AppCompatActivity(),Utils {

    private val btRegister by lazy { findViewById<Button>(R.id.bt_register) }
    private val fieldEmailLayout by lazy { findViewById<TextInputLayout>(R.id.til_email_login) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
        initClick()

    }

    private fun initClick(){
        btRegister.setOnClickListener{
            val intent = Intent(this,RegisterScreen::class.java)
            validateEmail(fieldEmailLayout)
        }
    }
}