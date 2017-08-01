package com.github.kamimoo.kotlinarchtecture.data.repository

import com.github.kamimoo.kotlinarchtecture.api.QiitaService
import com.github.kamimoo.kotlinarchtecture.data.model.Token
import com.github.kamimoo.kotlinarchtecture.data.model.TokenParams
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Mockito.`when`


class UserRepositoryTest {

    val qiitaService: QiitaService = mock()

    val repository = UserRepository(qiitaService)

    @Test
    fun accessToken() {
        `when`(qiitaService.accessTokens(TokenParams("id", "secret", "code")))
            .thenReturn(Single.just(
                Token("access_token", listOf("read_qiita"))
            ))

        val token = repository.accessToken("id", "secret", "code").blockingGet()

        assertThat(token).isNotNull()
        assertThat(token.token).isEqualTo("access_token")
        assertThat(token.scopes).containsExactly("read_qiita")
    }
}
