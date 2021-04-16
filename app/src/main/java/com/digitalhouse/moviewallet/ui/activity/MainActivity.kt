package com.digitalhouse.moviewallet.ui.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProviders
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.ui.viewmodel.HomeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        goToHome()


    }

    private fun goToHome() {
        Handler().postDelayed({
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
            finish()
        }, 999)
    }
}