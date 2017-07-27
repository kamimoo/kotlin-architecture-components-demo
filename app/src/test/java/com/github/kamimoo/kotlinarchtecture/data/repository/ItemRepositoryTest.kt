package com.github.kamimoo.kotlinarchtecture.data.repository

import com.github.kamimoo.kotlinarchtecture.api.QiitaService
import com.github.kamimoo.kotlinarchtecture.data.model.Item
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import okhttp3.Headers
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import retrofit2.Response


class ItemRepositoryTest {

    val qiitaService: QiitaService = mock()

    val repository = ItemRepository(qiitaService)

    @Test
    fun items() {
        val item1 = Item("a", "http://qiita.com/a/items/aabbcc123defg04d5ace")
        val item2 = Item("b", "http://qiita.com/b/items/000000000abcdef00000")
        `when`(qiitaService.getItems(null, 1))
            .thenReturn(Single.just(
                Response.success(listOf(
                    item1, item2
                ))
            ))

        val (items, page) = repository.getItems().blockingGet()
        assertThat(page).isNull()
        assertThat(items).containsExactly(item1, item2)
    }

    @Test
    fun itemsWithLinkHeader() {
        val header = "<https://qiita.com/api/v2/items?page=1>; rel=\"first\", <https://qiita.com/api/v2/items?page=2>; rel=\"next\", <https://qiita.com/api/v2/items?page=11650>; rel=\"last\""
        val headers = mapOf("Link" to header)
        val item1 = Item("a", "http://qiita.com/a/items/aabbcc123defg04d5ace")
        val item2 = Item("b", "http://qiita.com/b/items/000000000abcdef00000")
        `when`(qiitaService.getItems(null, 1))
            .thenReturn(Single.just(
                Response.success(listOf(
                    item1, item2
                ), Headers.of(headers))
            ))

        val (items, page) = repository.getItems().blockingGet()
        assertThat(page).isEqualTo(2)
        assertThat(items).containsExactly(item1, item2)
    }
}
