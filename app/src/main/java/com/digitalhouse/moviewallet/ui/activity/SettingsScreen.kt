package com.digitalhouse.moviewallet.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.digitalhouse.moviewallet.R
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class SettingsScreen : AppCompatActivity() {

    private val buttonLogout by lazy { findViewById<Button>(R.id.btn_logout) }
    private val userName by lazy { findViewById<TextView>(R.id.user_name_preferencias) }
    private val toolbarSettings by lazy { findViewById<Toolbar>(R.id.toobar) }
    private val userPhoto by lazy { findViewById<ImageView>(R.id.foto_preferencias) }
    private lateinit var firebaseAuth: FirebaseAuth
    private val loginManager = LoginManager.getInstance()
    private lateinit var googleSingInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_screen)
        setSupportActionBar(toolbarSettings)
        toolbarSettings.setNavigationOnClickListener { onBackPressed() }

        firebaseAuth = FirebaseAuth.getInstance()

        val googleSignOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSingInClient = GoogleSignIn.getClient(this, googleSignOptions)

        buttonLogout.setOnClickListener {
            signout()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        setUserName(currentUser?.displayName)
        Picasso.get().load(currentUser.photoUrl).into(userPhoto)

    }

    private fun setUserName(name: String?) {
        userName.text = name ?: ""
    }

    private fun signout() {
        firebaseAuth.signOut()
        loginManager.logOut()
        googleSingInClient.signOut()

        setUserName("Usuário desconectado")
        finish()
    }
}