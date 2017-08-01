package com.github.kamimoo.kotlinarchtecture.data.model

import com.squareup.moshi.Json

data class TokenParams(
    @Json(name = "client_id")
    val id: String,
    @Json(name = "client_secret")
    val secret: String,
    val code: String
)
