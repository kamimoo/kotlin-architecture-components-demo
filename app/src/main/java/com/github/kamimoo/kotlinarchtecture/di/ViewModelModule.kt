package com.github.kamimoo.kotlinarchtecture.di

import com.github.kamimoo.kotlinarchtecture.data.AuthorizationStorage
import com.github.kamimoo.kotlinarchtecture.data.repository.ItemRepository
import com.github.kamimoo.kotlinarchtecture.data.repository.UserRepository
import com.github.kamimoo.kotlinarchtecture.ui.AuthorizationViewModelFactory
import com.github.kamimoo.kotlinarchtecture.ui.SearchViewModelFactory
import dagger.Module
import dagger.Provides


@Module
class ViewModelModule {

    @Provides
    fun provideViewModelFactory(userRepository: UserRepository,
                                authorizationStorage: AuthorizationStorage): AuthorizationViewModelFactory =
        AuthorizationViewModelFactory(userRepository, authorizationStorage)

    @Provides
    fun provideSearchViewModelFactory(itemRepository: ItemRepository): SearchViewModelFactory =
        SearchViewModelFactory(itemRepository)
}
