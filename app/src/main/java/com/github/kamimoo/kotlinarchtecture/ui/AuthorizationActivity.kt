package com.github.kamimoo.kotlinarchtecture.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.kamimoo.kotlinarchtecture.BuildConfig
import com.github.kamimoo.kotlinarchtecture.R
import com.github.kamimoo.kotlinarchtecture.data.AuthorizationStorage
import dagger.android.AndroidInjection
import javax.inject.Inject


class AuthorizationActivity : AppCompatActivity() {

    @Inject
    lateinit var authStorage: AuthorizationStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)
        findViewById<View>(R.id.sign_in).setOnClickListener { showQiitaAuthPage() }
    }

    override fun onResume() {
        super.onResume()

        intent.data?.let {
            val code = it.getQueryParameter("code")
            authStorage.accessToken = code
            val newIntent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            startActivity(newIntent)
            finish()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.intent = intent
    }

    fun showQiitaAuthPage() {
        val authUri = Uri.parse("https://qiita.com/api/v2/oauth/authorize/")
            .buildUpon().apply {
            appendQueryParameter("client_id", BuildConfig.QIITA_CLIENT_ID)
            appendQueryParameter("scope", "read_qiita")
        }.build()
        CustomTabsIntent
            .Builder().apply {
            setShowTitle(true)
            setToolbarColor(ContextCompat.getColor(this@AuthorizationActivity, R.color.colorPrimary))
        }.build()
            .launchUrl(this, authUri)

    }
}
