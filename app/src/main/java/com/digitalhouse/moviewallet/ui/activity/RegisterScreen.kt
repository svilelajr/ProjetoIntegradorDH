package com.digitalhouse.moviewallet.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.ui.viewmodel.RegisterViewModel
import com.digitalhouse.moviewallet.util.Util
import com.google.android.material.textfield.TextInputLayout

class RegisterScreen : AppCompatActivity(),Util {
    private val fieldNameLayout by lazy { findViewById<TextInputLayout>(R.id.til_name_register) }
    private val fieldEmailLayout by lazy { findViewById<TextInputLayout>(R.id.til_email_register) }
    private val fieldPasswordLayout by lazy { findViewById<TextInputLayout>(R.id.til_password_register) }
    private val fieldConfirmPasswordLayout by lazy {findViewById<TextInputLayout>(R.id.til_confirmpassword_register) }
    private val spinnerGender by lazy { findViewById<Spinner>(R.id.spnCadastroGenero) }
    private val btnConfirm by lazy { findViewById<Button>(R.id.confirm_button)}

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)
        viewModel = ViewModelProvider.NewInstanceFactory().create(RegisterViewModel::class.java)

        //Criando uma lista de opções para o Spinner
        val listaGenero = arrayListOf("Selecione o gênero", "Feminino", "Masculino", "Não-binário")

        //Criando um adaptador para o spinner
        val generoAdapter = ArrayAdapter(
                this,                                               //contexto
                android.R.layout.simple_spinner_dropdown_item,      //layout
                listaGenero                                         //Dados
        )

        //Plugar o adaptador no Spinner
        spinnerGender.adapter = generoAdapter

        initClick()
    }

    private fun initClick() {
        btnConfirm.setOnClickListener{
            if(validateName(fieldNameLayout) && validateEmail(fieldEmailLayout) && validadePassword(fieldPasswordLayout) && validadeConfirmPassword(fieldPasswordLayout,fieldConfirmPasswordLayout)){
                val intent = Intent(this,HomeScreen::class.java)
                startActivity(intent)
            }
        }
    }
}