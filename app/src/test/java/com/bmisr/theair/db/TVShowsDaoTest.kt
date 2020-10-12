package com.bmisr.theair.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bmisr.theair.api.response.TVShow
import com.tiendito.bmisrmovies.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TVShowsDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var tvShowsDatabase: TVShowsDatabase

    @Before
    fun initDB() {

        tvShowsDatabase =  Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TVShowsDatabase::class.java)
            .setTransactionExecutor(Dispatchers.IO.asExecutor())
            .setQueryExecutor(Dispatchers.IO.asExecutor())
            .build()
    }

    @After
    fun closeDb() {
        tvShowsDatabase.close()
    }

    @Test
   fun insertMovieTest() {
       val movie  = createMovie(550)
       runBlocking {
           tvShowsDatabase.tvShowsDao().insert(movie)
           val movies =  tvShowsDatabase.tvShowsDao().loadTVShows()
            assert(movies.getOrAwaitValue().isNotEmpty())
       }
   }

    @Test
    fun loadMovieByIdTest() {
        val movieList  = listOf(createMovie(550), createMovie(432), createMovie(443))
        runBlocking {
            tvShowsDatabase.tvShowsDao().insertTVShows(movieList)
            val movies =  tvShowsDatabase.tvShowsDao().loadTVShowById(432)
            assertThat(movies.getOrAwaitValue()?.id, `is`(432))
        }
    }

    @Test
    fun deleteMovieTest() {
        val movieList  = listOf(createMovie(550), createMovie(432), createMovie(443))
        runBlocking {
            tvShowsDatabase.tvShowsDao().insertTVShows(movieList)
            tvShowsDatabase.tvShowsDao().deleteTVShow(550)

            val movies =  tvShowsDatabase.tvShowsDao().loadTVShowById(550)
            assert(movies.getOrAwaitValue() == null)
        }
    }

    
    private fun createMovie(id: Int) : TVShow {
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