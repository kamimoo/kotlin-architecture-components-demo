package com.github.kamimoo.kotlinarchtecture

import com.github.kamimoo.kotlinarchtecture.di.DaggerAppComponent
import dagger.android.support.DaggerApplication
import timber.log.Timber


class MainApplication : DaggerApplication() {

    override fun applicationInjector() =
        DaggerAppComponent.builder().create(this)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
