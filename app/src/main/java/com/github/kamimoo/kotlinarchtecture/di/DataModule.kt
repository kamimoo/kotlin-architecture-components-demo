package com.github.kamimoo.kotlinarchtecture.di

import android.app.Application
import dagger.Provides
import android.preference.PreferenceManager
import android.content.SharedPreferences
import dagger.Module


@Module
class DataModule {

    @Provides
    fun provideSharedPreferences(app: Application): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(app)
}
