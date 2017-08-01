package com.github.kamimoo.kotlinarchtecture.ui

import android.arch.lifecycle.ViewModel
import com.github.kamimoo.kotlinarchtecture.BuildConfig
import com.github.kamimoo.kotlinarchtecture.data.AuthorizationStorage
import com.github.kamimoo.kotlinarchtecture.data.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class AuthorizationViewModel @Inject constructor(
    private val repository: UserRepository,
    private val authStorage: AuthorizationStorage
) : ViewModel() {

    var code: String = ""

    fun updateToken() =
        repository.accessToken(BuildConfig.QIITA_CLIENT_ID, BuildConfig.QIITA_CLIENT_SECRET, code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { (token, scopes) ->
                authStorage.accessToken = token
            }
}
