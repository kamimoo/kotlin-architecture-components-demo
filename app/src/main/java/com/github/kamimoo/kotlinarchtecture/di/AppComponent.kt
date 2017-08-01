package com.github.kamimoo.kotlinarchtecture.di

import com.github.kamimoo.kotlinarchtecture.MainApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = arrayOf(
        AndroidSupportInjectionModule::class,
        AndroidBindingModule::class,
        AppModule::class,
        DataModule::class,
        ViewModelModule::class
    )
)
interface AppComponent : AndroidInjector<MainApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MainApplication>()
}
