package com.github.kamimoo.kotlinarchtecture.di

import com.github.kamimoo.kotlinarchtecture.ui.AuthorizationActivity
import com.github.kamimoo.kotlinarchtecture.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class AndroidBindingModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun authorizationActivity(): AuthorizationActivity
}
