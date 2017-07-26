package com.github.kamimoo.kotlinarchtecture.api

import com.github.kamimoo.kotlinarchtecture.data.model.Item
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QiitaService {

    @GET("items")
    fun getItems(@Query("query") query: String? = null, @Query("page") page: Int? = null): Single<Response<List<Item>>>
}
