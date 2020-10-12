package com.bmisr.theair.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bmisr.theair.utils.Constants.API_KEY
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers
import org.junit.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Mohamed Salama on 10/12/2020.
 * Email: msalama.tiendito@gmail.com
 */

class TVShowApisTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var tvShowsApis: TVShowsApis

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createWebService(){
        mockWebServer = MockWebServer()
        tvShowsApis = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TVShowsApis::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun testNowPlayingMovies() {
        enqueueResponse("tv_shows_response.json")
        runBlocking {
            val result = tvShowsApis.getLatestTVShows(apiKey = API_KEY)

            Assert.assertThat(result.body()?.tvShows?.size, CoreMatchers.`is`(20))
            val movie1 = result.body()?.tvShows?.get(0)
            Assert.assertThat(movie1?.name, CoreMatchers.`is`("The Walking Dead: World Beyond"))

        }

    }

    @Test
    fun testMovieCast() {
        enqueueResponse("cast_response.json")
        runBlocking {
            val result = tvShowsApis.getTvShowCast(tvShowId = 550, apiKey = API_KEY)

            Assert.assertThat(result.body()?.cast?.size, CoreMatchers.`is`(79))
            val cast1 = result.body()?.cast?.get(0)
            Assert.assertThat(cast1?.character, CoreMatchers.`is`("The Narrator"))

        }

    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }

}