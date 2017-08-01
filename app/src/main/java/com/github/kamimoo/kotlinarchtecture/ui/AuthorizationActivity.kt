package com.github.kamimoo.kotlinarchtecture.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.github.kamimoo.kotlinarchtecture.BuildConfig
import com.github.kamimoo.kotlinarchtecture.R
import com.google.common.hash.Hashing
import dagger.android.AndroidInjection
import timber.log.Timber
import java.nio.charset.Charset
import java.util.*
import javax.inject.Inject


class AuthorizationActivity : AppCompatActivity() {

    companion object {
        private const val STATE_SHA1 = "state_sha1"
        private const val PARAM_CLIENT_ID = "client_id"
        private const val PARAM_SCOPE = "scope"
        private const val PARAM_STATE = "state"
        private const val PARAM_CODE = "code"
    }

    @Inject
    lateinit var viewModelFactory: AuthorizationViewModelFactory

    private lateinit var viewModel: AuthorizationViewModel

    private var state: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AuthorizationViewModel::class.java)

        setContentView(R.layout.activity_authorization)
        findViewById<View>(R.id.sign_in).setOnClickListener { showQiitaAuthPage() }
    }

    override fun onResume() {
        super.onResume()

        intent.data?.let {
            val code = it.getQueryParameter(PARAM_CODE)
            if (!state.equals(it.getQueryParameter(PARAM_STATE))) {
                Timber.e("CSRF code mismatch")
                Toast.makeText(this, R.string.authorization_failed, Toast.LENGTH_LONG).show()
            } else {
                viewModel.code = code
                viewModel.updateToken()
                    .subscribe { _ ->
                        val newIntent = Intent(this, MainActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        }
                        startActivity(newIntent)
                        finish()
                    }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.intent = intent
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(STATE_SHA1, state)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        state = savedInstanceState?.getString(STATE_SHA1)
    }

    fun showQiitaAuthPage() {
        state = Hashing.sha1().hashString(UUID.randomUUID().toString(), Charset.forName("UTF-8")).toString()
        val authUri = Uri.parse("https://qiita.com/api/v2/oauth/authorize/")
            .buildUpon().apply {
            appendQueryParameter(PARAM_CLIENT_ID, BuildConfig.QIITA_CLIENT_ID)
            appendQueryParameter(PARAM_SCOPE, "read_qiita")
            appendQueryParameter(PARAM_STATE, state)
        }.build()
        CustomTabsIntent
            .Builder().apply {
            setShowTitle(true)
            setToolbarColor(ContextCompat.getColor(this@AuthorizationActivity, R.color.colorPrimary))
        }.build()
            .launchUrl(this, authUri)

    }
}
