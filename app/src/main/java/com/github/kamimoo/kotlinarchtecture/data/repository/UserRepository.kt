package com.github.kamimoo.kotlinarchtecture.data.repository

import com.github.kamimoo.kotlinarchtecture.api.QiitaService
import com.github.kamimoo.kotlinarchtecture.data.model.TokenParams
import javax.inject.Inject


class UserRepository @Inject constructor(private val qiitaService: QiitaService) {

    fun accessToken(id: String, secret: String, code: String) =
        qiitaService.accessTokens(TokenParams(id, secret, code))
}
