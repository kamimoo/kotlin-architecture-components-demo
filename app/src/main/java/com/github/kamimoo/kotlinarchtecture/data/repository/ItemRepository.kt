package com.github.kamimoo.kotlinarchtecture.data.repository

import com.github.kamimoo.kotlinarchtecture.api.QiitaService
import com.github.kamimoo.kotlinarchtecture.data.model.Item
import io.reactivex.Single
import java.util.regex.Pattern
import javax.inject.Inject


class ItemRepository @Inject constructor(private val qiitaService: QiitaService) {

    companion object {

        private val NEXT_PAGE_PATTERN = Pattern
            .compile("<[^>]*\\?page=(\\d+)[^>]*>[\\s]*;[\\s]*rel=\"next\"")
    }

    fun getItems(query: String = "", page: Int = 1): Single<Pair<List<Item>, Int?>> {
        val q = if (query.isEmpty()) null else query
        return qiitaService.getItems(q, page)
            .map {
                val nextPage = it.headers()["Link"]?.let {
                    val m = NEXT_PAGE_PATTERN.matcher(it)
                    if (m.find()) {
                        Integer.parseInt(m.group(1))
                    } else {
                        null
                    }
                }
                return@map (it.body() ?: emptyList<Item>()) to nextPage
            }
    }
}
