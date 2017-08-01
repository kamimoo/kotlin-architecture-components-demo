package com.github.kamimoo.kotlinarchtecture.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.github.kamimoo.kotlinarchtecture.BuildConfig
import com.github.kamimoo.kotlinarchtecture.api.QiitaService
import com.github.kamimoo.kotlinarchtecture.data.AuthorizationStorage
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
class DataModule {

    @Provides
    fun provideSharedPreferences(app: Application): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(app)

    @Provides
    fun provideAuthorizationStorage(prefs: SharedPreferences): AuthorizationStorage =
        AuthorizationStorage(prefs)

    @Singleton
    @Provides
    fun provideOkHttp(storage: AuthorizationStorage): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                addNetworkInterceptor(StethoInterceptor())
            }
            addInterceptor {
                val request = it.request().run {
                    val headers = headers().newBuilder().add("Authorization", "Bearer ${storage.accessToken}").build()
                    newBuilder().headers(headers).build()
                }
                it.proceed(request)
            }
        }.build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://qiita.com/api/v2/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideQiitaService(retrofit: Retrofit): QiitaService =
        retrofit.create(QiitaService::class.java)
}
