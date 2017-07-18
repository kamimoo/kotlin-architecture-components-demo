package com.github.kamimoo.kotlinarchtecture.data

import android.content.SharedPreferences


const val TOKEN_KEY = "access_token"

class AuthorizationStorage(private val prefs: SharedPreferences) {

    var accessToken: String
        get() = prefs.getString(TOKEN_KEY, "")
        set(value) {
            prefs.edit().putString(TOKEN_KEY, value).apply()
        }

    fun clearToken() {
        prefs.edit().remove(TOKEN_KEY)
    }
}
