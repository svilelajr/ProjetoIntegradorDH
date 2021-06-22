package com.digitalhouse.moviewallet.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.ui.viewmodel.RegisterViewModel
import com.digitalhouse.moviewallet.util.Util
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterScreen : AppCompatActivity(), Util {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var firebaseAuth: FirebaseAuth


    private val fieldNameLayout by lazy { findViewById<TextInputLayout>(R.id.til_name_register) }
    private val fieldName by lazy { findViewById<TextInputEditText>(R.id.et_name_register) }
    private val fieldEmailLayout by lazy { findViewById<TextInputLayout>(R.id.til_email_register) }
    private val fieldEmail by lazy { findViewById<TextInputEditText>(R.id.et_email_register) }
    private val fieldPasswordLayout by lazy { findViewById<TextInputLayout>(R.id.til_password_register) }
    private val fieldPassword by lazy { findViewById<TextInputEditText>(R.id.et_password_register) }
    private val fieldConfirmPasswordLayout by lazy { findViewById<TextInputLayout>(R.id.til_confirmpassword_register) }
    private val fieldConfirmPassword by lazy { findViewById<TextInputEditText>(R.id.et_confirmpassword_register) }
    private val btnConfirm by lazy { findViewById<Button>(R.id.confirm_button) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)

        viewModel = ViewModelProvider.NewInstanceFactory().create(RegisterViewModel::class.java)

        firebaseAuth = Firebase.auth

        initClick()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        setUserEmail(currentUser?.email ?: "Usuário Desconectado")
    }

    private fun createUser() {
        val name = fieldName.text.toString()
        val email = fieldEmail.text.toString()
        val pass = fieldPassword.text.toString()
        val confirmPassword = fieldConfirmPassword.text.toString()


        if (pass == confirmPassword) {
            createUserWithEmailPass(email, pass)
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Senha inválida")
            builder.setMessage("Senha não é igual a confirmação de senha\n Digite novamente")
            builder.show()
        }

    }


    private fun createUserWithEmailPass(email: String, pass: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = firebaseAuth.currentUser
                setUserEmail(user?.email ?: "Usuário desconectado")
            } else {
                setUserEmail(task.exception?.message!!)
            }
        }
    }


    private fun setUserEmail(email: String) {
        setOf(email)
    }

    private fun signout(view: View) {
        firebaseAuth.signOut()
        setUserEmail("Usuário desconectado")
    }


    private fun initClick() {
        btnConfirm.setOnClickListener {
            if (validateName(fieldNameLayout) && validateEmail(fieldEmailLayout) && validadePassword(
                    fieldPasswordLayout
                ) && validadeConfirmPassword(fieldPasswordLayout, fieldConfirmPasswordLayout)
            ) {
                createUser()
                val intent = Intent(this, HomeScreen::class.java)
                startActivity(intent)
            }


        }
    }
}