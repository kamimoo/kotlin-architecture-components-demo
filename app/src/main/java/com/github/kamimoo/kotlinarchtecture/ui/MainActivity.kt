package com.github.kamimoo.kotlinarchtecture.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.github.kamimoo.kotlinarchtecture.R
import com.github.kamimoo.kotlinarchtecture.data.AuthorizationStorage
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var authStorage: AuthorizationStorage

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

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
        if (savedInstanceState == null) {
            SearchFragment.newInstance().let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, it)
                    .commitAllowingStateLoss()
            }
        }

    }
}
