package com.digitalhouse.moviewallet.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
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
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth

class LoginScreen: AppCompatActivity(),Util {

    private val btLogin by lazy { findViewById<Button>(R.id.bt_login)}
    private val btRegister by lazy { findViewById<Button>(R.id.bt_register)}
    private val fieldEmailLayout by lazy { findViewById<TextInputLayout>(R.id.til_email_login) }
    private val fieldPasswordLayout by lazy { findViewById<TextInputLayout>(R.id.til_password_login) }
    private val btLoginFacebook by lazy { findViewById<ImageButton>(R.id.img_btn_facebook_sign_in) }
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager
    private val loginManager = LoginManager.getInstance()

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
        viewModel = ViewModelProvider.NewInstanceFactory().create(LoginViewModel::class.java)

        firebaseAuth = FirebaseAuth.getInstance()

        callbackManager = CallbackManager.Factory.create()
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

        btLoginFacebook.setOnClickListener {
            loginFacebook()
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
//                    setUserName(name)
                } else {
                    Log.w("facebook", "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
//                    setUserName("Usuário desconectado")
                }
            }
    }


    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        if(currentUser != null){
            startActivity(Intent(this, SettingsScreen::class.java))
            finish()
        }
    }

    fun signinFace(view: View) {
        loginFacebook()
    }

    fun signout(view: View) {
        firebaseAuth.signOut()
        loginManager.logOut()

//        setUserName("Usuário desconectado")
    }

}