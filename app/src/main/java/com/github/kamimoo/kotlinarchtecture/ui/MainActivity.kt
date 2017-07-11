package com.github.kamimoo.kotlinarchtecture.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.kamimoo.kotlinarchtecture.R
import com.github.kamimoo.kotlinarchtecture.data.AuthorizationStorage
import dagger.android.AndroidInjection
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var authStorage: AuthorizationStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (authStorage.accessToken.isEmpty()) {
            val newIntent = Intent(this, AuthorizationActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            startActivity(newIntent)
            finish()
        }

    }
}
