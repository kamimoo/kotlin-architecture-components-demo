package com.github.kamimoo.kotlinarchtecture.di

import android.app.Application
import com.github.kamimoo.kotlinarchtecture.MainApplication
import dagger.Binds
import dagger.Module


@Module
abstract class AppModule {

    @Binds
    abstract fun application(application: MainApplication): Application
}
