package com.digitalhouse.moviewallet.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.digitalhouse.moviewallet.R
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class SettingsScreen : AppCompatActivity() {

    private val buttonLogout: Button by lazy { findViewById(R.id.btn_logout) }
    private val userName: TextView by lazy { findViewById(R.id.user_name_preferencias) }
    private val toolbarSettings by lazy { findViewById<Toolbar>(R.id.toobar) }
    private val userPhoto:ImageView by lazy { findViewById(R.id.foto_preferencias) }
    private lateinit var firebaseAuth: FirebaseAuth
    private val loginManager = LoginManager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_screen)
        setSupportActionBar(toolbarSettings)
        toolbarSettings.setNavigationOnClickListener { onBackPressed() }

        firebaseAuth = FirebaseAuth.getInstance()

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

    fun signout() {
        firebaseAuth.signOut()
        loginManager.logOut()

        setUserName("Usuário desconectado")
        finish()
    }
}