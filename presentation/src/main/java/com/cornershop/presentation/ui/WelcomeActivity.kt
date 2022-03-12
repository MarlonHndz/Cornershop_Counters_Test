package com.cornershop.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cornershop.presentation.R

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }

    fun navigateToHome(view: View) {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
