package com.bmisr.theair.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bmisr.theair.api.TVShowsApis
import com.bmisr.theair.api.response.TVShow
import com.bmisr.theair.api.response.TVShowsResponse
import com.bmisr.theair.db.TVShowsDao
import com.bmisr.theair.db.TVShowsDatabase
import com.bmisr.theair.model.Resource
import com.bmisr.theair.utils.Constants.API_KEY
import com.bmisr.theair.mock
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import retrofit2.Response

/**
 * Created by Mohamed Salama on 10/12/2020.
 */

@RunWith(AndroidJUnit4::class)
class TVShowsRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var tvShowsRepository: TVShowsRepository

    private val sessionRepository = Mockito.mock(SessionRepository::class.java)
    private val service = Mockito.mock(TVShowsApis::class.java)
    private val dao = Mockito.mock(TVShowsDao::class.java)

    @Before
    fun init() {
        val db = Mockito.mock(TVShowsDatabase::class.java)
        Mockito.`when`(db.tvShowsDao()).thenReturn(dao)
        Mockito.`when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        tvShowsRepository = TVShowsRepository(service, sessionRepository, dao)
    }


    @Test
    fun loadTVShowsTest() {
        val observer = mock<Observer<Resource<List<TVShow>>>>()

        val tvShowResponse = TVShowsResponse(emptyList())

        val apiResponse = Response.success(tvShowResponse)

        runBlocking {
            Mockito.`when`(service.getOnTheAirTVShows(apiKey = API_KEY)).thenReturn(apiResponse)

            tvShowsRepository.loadLatestTVShows().observeForever(observer)

            Mockito.verify(observer).onChanged(Resource.loading(null))
            Mockito.verify(observer).onChanged(Resource.complete(null))
            Mockito.verify(observer).onChanged(Resource.success(emptyList()))
        }
    }

    @Test
    fun isFavourite_Return_True_Test() {
        val tvShow = MutableLiveData<TVShow>()
        val observer = mock<Observer<Boolean>>()

        tvShow.postValue(createTVShow(550))

        Mockito.`when`(dao.loadTVShowById(550)).thenReturn(tvShow)

        tvShowsRepository.isFavouriteTVShow(550).observeForever(observer)

        Mockito.verify(observer).onChanged(true)

    }

    @Test
    fun isFavourite_Return_False_Test() {
        val tvShow = MutableLiveData<TVShow>()
        val observer = mock<Observer<Boolean>>()

        tvShow.postValue(null)

        Mockito.`when`(dao.loadTVShowById(550)).thenReturn(tvShow)

        tvShowsRepository.isFavouriteTVShow(550).observeForever(observer)

        Mockito.verify(observer).onChanged(false)

    }

    @Test
    fun loadAllFavouritesTest() {
        val observer = mock<Observer<Resource<List<TVShow>>>>()

        val tvShows = MutableLiveData<List<TVShow>>()

        Mockito.`when`(dao.loadTVShows()).thenReturn(tvShows)

        tvShowsRepository.loadFavouriteTVShows().observeForever(observer)

        Mockito.verify(observer).onChanged(Resource.loading(null))

        tvShows.postValue(emptyList())

        Mockito.verify(observer).onChanged(Resource.complete(null))
        Mockito.verify(observer).onChanged(Resource.success(emptyList()))

    }

    private fun createTVShow(id: Int) : TVShow {
        return TVShow(
            id = id,
            name = "breaking bad",
            firstAirDate = "2014-10-20",
            voteAverage = 5.5,
            posterPath = "",
            backdropPath = "",
            overview = "",
            genres = emptyList(),
            episodeNumber = 43,
            homepage = "",
            networks = emptyList()
        )
    }

}