package com.digitalhouse.moviewallet.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.ui.viewmodel.LoginViewModel
import com.digitalhouse.moviewallet.util.Util
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.lang.Exception

class LoginScreen : AppCompatActivity(), Util {

    private val btLogin by lazy { findViewById<Button>(R.id.bt_login) }
    private val btRegister by lazy { findViewById<Button>(R.id.bt_register) }
    private val btLoginFacebook by lazy { findViewById<ImageButton>(R.id.img_btn_facebook_sign_in) }
    private val btLoginGoogle by lazy { findViewById<ImageButton>(R.id.img_btn_google_sign_in) }
    private val fieldEmailLayout by lazy { findViewById<TextInputLayout>(R.id.til_email_login) }
    private val fieldEmail by lazy { findViewById<TextInputEditText>(R.id.et_email_login) }
    private val fieldPasswordLayout by lazy { findViewById<TextInputLayout>(R.id.til_password_login) }
    private val fieldPassword by lazy { findViewById<TextInputEditText>(R.id.ti_password_login) }
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager
    private val loginManager = LoginManager.getInstance()
    private lateinit var googleSingInClient: GoogleSignInClient

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
        viewModel = ViewModelProvider.NewInstanceFactory().create(LoginViewModel::class.java)

        firebaseAuth = FirebaseAuth.getInstance()

        callbackManager = CallbackManager.Factory.create()

        initClick()

        val googleSignOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSingInClient = GoogleSignIn.getClient(this, googleSignOptions)

    }

    private fun initClick() {
        btLogin.setOnClickListener {
            if (validadePassword(fieldPasswordLayout) && validateEmail(fieldEmailLayout)) {
                val email = fieldEmail.text.toString()
                val pass = fieldPassword.text.toString()
                firebaseAuthWithEmailPass(email, pass)

            }
        }

        btRegister.setOnClickListener {
            val intent = Intent(this, RegisterScreen::class.java)
            startActivity(intent)
        }

        btLoginFacebook.setOnClickListener {
            loginFacebook()
        }

        btLoginGoogle.setOnClickListener {
            loginGoogleSignIn()
        }
    }

    fun loginFacebook() {
        loginManager.logInWithReadPermissions(this, arrayListOf("email", "public_profile"))
        loginManager.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d("facebook", "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d("facebook", "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d("facebook", "facebook:onError", error)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 200) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                val account = task.getResult(ApiException::class.java)!!
                Log.d("GogleSign", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {

                Log.w("GogleSign", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Log.d("GoogleSign", "signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                    startActivity(Intent(this, HomeScreen::class.java))

                } else {

                    Log.w("GoogleSign", "signInWithCredential:failure", task.exception)
                    startActivity(Intent(this, HomeScreen::class.java))

                }
            }

    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("facebook", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Log.d("facebook", "signInWithCredential:success")
                    val name = firebaseAuth.currentUser?.displayName
                    startActivity(Intent(this, HomeScreen::class.java))

                } else {

                    Log.w("facebook", "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {

            startActivity(Intent(this, SettingsScreen::class.java))
            finish()

        }
    }

    fun loginGoogleSignIn() {
        val signInIntent = googleSingInClient.signInIntent

        startActivityForResult(signInIntent, 200)

    }

    private fun firebaseAuthWithEmailPass(email: String, pass: String) {
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = firebaseAuth.currentUser
                startActivity(Intent(this, HomeScreen::class.java))
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Login Inválido")
                builder.setMessage("Usuário não cadastrado")
                builder.show()
            }
        }
    }

}