package com.digitalhouse.moviewallet.ui.activity
//
//import android.content.Intent
//import android.widget.ImageButton
//import com.digitalhouse.moviewallet.R
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.Toast
//import com.facebook.AccessToken
//import com.facebook.CallbackManager
//import com.facebook.FacebookCallback
//import com.facebook.FacebookException
//import com.facebook.login.LoginManager
//import com.facebook.login.LoginResult
//import com.google.firebase.auth.FacebookAuthProvider
//import com.google.firebase.auth.FirebaseAuth
//
//class LoginFacebook {
//
//    private val btLoginFacebook by lazy { findViewById<ImageButton>(R.id.img_btn_facebook_sign_in) }
//    private lateinit var firebaseAuth: FirebaseAuth
//    private lateinit var callbackManager: CallbackManager
//    private val loginManager = LoginManager.getInstance()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView()
//
//        firebaseAuth = FirebaseAuth.getInstance()
//
//        callbackManager = CallbackManager.Factory.create()
//
//        initClick()
//
//    }
//
//    private fun initClick(){
//
//        btLoginFacebook.setOnClickListener {
//            loginFacebook()
//        }
//    }
//
//    fun loginFacebook() {
//        loginManager.logInWithReadPermissions(this, arrayListOf("email", "public_profile"))
//        loginManager.registerCallback(callbackManager, object :
//            FacebookCallback<LoginResult> {
//            override fun onSuccess(loginResult: LoginResult) {
//                Log.d("facebook", "facebook:onSuccess:$loginResult")
//                handleFacebookAccessToken(loginResult.accessToken)
//            }
//
//            override fun onCancel() {
//                Log.d("facebook", "facebook:onCancel")
//            }
//
//            override fun onError(error: FacebookException) {
//                Log.d("facebook", "facebook:onError", error)
//            }
//        })
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        callbackManager.onActivityResult(requestCode, resultCode, data)
//    }
//
//    private fun handleFacebookAccessToken(token: AccessToken) {
//        Log.d("facebook", "handleFacebookAccessToken:$token")
//
//        val credential = FacebookAuthProvider.getCredential(token.token)
//        firebaseAuth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    Log.d("facebook", "signInWithCredential:success")
//                    val name = firebaseAuth.currentUser?.displayName
//                    setUserName(name)
//                } else {
//                    Log.w("facebook", "signInWithCredential:failure", task.exception)
//                    Toast.makeText(
//                        baseContext, "Authentication failed.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    setUserName("Usuário desconectado")
//                }
//            }
//    }
//
//    private fun setUserName(name: String?) {
//        emailTv.text = name ?: ""
//    }
//
//    override fun onStart() {
//        super.onStart()
//        val currentUser = firebaseAuth.currentUser
//        setUserName(currentUser?.displayName)
//    }
//
//    fun signinFace(view: View) {
//        loginFacebook()
//    }
//
//    fun signout(view: View) {
//        firebaseAuth.signOut()
//        loginManager.logOut()
//
//        setUserName("Usuário desconectado")
//    }
//
//}