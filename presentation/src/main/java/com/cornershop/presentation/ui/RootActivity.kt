package com.cornershop.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cornershop.domain.commons.Utils

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkIfBeenShown()
    }

    private fun checkIfBeenShown() {
        val preferences = getSharedPreferences(Utils.MY_PREFERENCES, Context.MODE_PRIVATE)
        val intent: Intent
        if (preferences.getBoolean(Utils.PREFERENCE_SHOW_WELCOME, true)) {
            intent = Intent(this, WelcomeActivity::class.java)
            val editor = preferences.edit()
            editor.putBoolean(Utils.PREFERENCE_SHOW_WELCOME, false)
            editor.apply()
        } else {
            intent = Intent(this, HomeActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}
