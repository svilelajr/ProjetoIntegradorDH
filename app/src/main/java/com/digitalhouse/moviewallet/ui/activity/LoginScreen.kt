package com.digitalhouse.moviewallet.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.ui.viewmodel.LoginViewModel
import com.digitalhouse.moviewallet.util.Util
import com.google.android.material.textfield.TextInputLayout

class LoginScreen: AppCompatActivity(),Util {

    private val btLogin by lazy { findViewById<Button>(R.id.bt_login)}
    private val btRegister by lazy { findViewById<Button>(R.id.bt_register)}
    private val fieldEmailLayout by lazy { findViewById<TextInputLayout>(R.id.til_email_login) }
    private val fieldPasswordLayout by lazy { findViewById<TextInputLayout>(R.id.til_password_login) }

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
        initClick()

    }

    private fun initClick(){
        btLogin.setOnClickListener{
           if (validadePassword(fieldPasswordLayout) && validateEmail(fieldEmailLayout)){
                val intent = Intent(this,HomeScreen::class.java)
                startActivity(intent)
          }
        }

        btRegister.setOnClickListener{
            val intent = Intent(this,RegisterScreen::class.java)
            startActivity(intent)
        }
    }
}