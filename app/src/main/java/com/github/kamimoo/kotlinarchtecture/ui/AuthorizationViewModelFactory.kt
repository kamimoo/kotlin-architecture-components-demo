package com.github.kamimoo.kotlinarchtecture.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.github.kamimoo.kotlinarchtecture.data.AuthorizationStorage
import com.github.kamimoo.kotlinarchtecture.data.repository.UserRepository
import javax.inject.Inject


class AuthorizationViewModelFactory
@Inject constructor(val repository: UserRepository,
                    val storage: AuthorizationStorage) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        return AuthorizationViewModel(repository, storage) as T
    }
}
