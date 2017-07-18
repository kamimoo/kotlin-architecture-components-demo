package com.github.kamimoo.kotlinarchtecture.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle


class AuthorizationCallbackActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val newIntent = Intent(this, AuthorizationActivity::class.java).apply {
            data = intent.data
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        startActivity(newIntent)
        finish()
    }
}
