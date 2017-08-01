package com.github.kamimoo.kotlinarchtecture.api

import com.github.kamimoo.kotlinarchtecture.data.model.Item
import com.github.kamimoo.kotlinarchtecture.data.model.Token
import com.github.kamimoo.kotlinarchtecture.data.model.TokenParams
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface QiitaService {

    @POST("access_tokens")
    fun accessTokens(@Body params: TokenParams): Single<Token>

    @GET("items")
    fun getItems(@Query("query") query: String? = null, @Query("page") page: Int? = null): Single<Response<List<Item>>>
}
