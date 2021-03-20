package com.digitalhouse.moviewalletsprint1.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.digitalhouse.moviewalletsprint1.R

class DetailsScreen : AppCompatActivity() {
    private val toolbar by lazy { findViewById<androidx.appcompat.widget.Toolbar>(R.id.tb_details) }
    private val ivMovie by lazy { findViewById<ImageView>(R.id.iv_movie_details) }
    private val tvMovie by lazy { findViewById<TextView>(R.id.tv_title_details) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_screen)
        initViews()
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initViews() {
        val extras = intent.extras
        val name = extras?.getString("NAME_MOVIE")
        val image = extras?.getInt("IMAGE_MOVIE")
        tvMovie.text = name
        image?.let { ivMovie.setImageResource(it) }

    }
}