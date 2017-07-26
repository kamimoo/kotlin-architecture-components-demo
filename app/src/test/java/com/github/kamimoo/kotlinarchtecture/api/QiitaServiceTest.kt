package com.github.kamimoo.kotlinarchtecture.api

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.nio.charset.StandardCharsets

@RunWith(JUnit4::class)
class QiitaServiceTest {

    lateinit var service: QiitaService
    val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        service = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(QiitaService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getItems() {
        enqueueResponse("items.json")
        val items = service.getItems("android").blockingGet()
        val request = mockWebServer.takeRequest()
        assertThat(request.path).isEqualTo("/items?query=android")
        assertThat(items).isNotNull()
        assertThat(items.body()!!.size).isEqualTo(3)

        val (title, url) = items.body()!![0]
        assertThat(title).isEqualTo("Android O")
        assertThat(url).isEqualTo("http://qiita.com/a/items/aabbcc123defg04d5ace")
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader
            .getResourceAsStream("api-response/" + fileName)
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        headers.forEach { key, value -> mockResponse.addHeader(key, value) }
        mockWebServer.enqueue(mockResponse
            .setBody(source.readString(StandardCharsets.UTF_8)))
    }
}
