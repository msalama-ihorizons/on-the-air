package com.bmisr.theair.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bmisr.theair.api.response.TVShow
import com.bmisr.theair.getOrAwaitValue
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
   fun insertTVShowTest() {
       val tvShow  = createTVShow(550)
       runBlocking {
           tvShowsDatabase.tvShowsDao().insert(tvShow)
           val tvShows =  tvShowsDatabase.tvShowsDao().loadTVShows()
            assert(tvShows.getOrAwaitValue().isNotEmpty())
       }
   }

    @Test
    fun loadTVShowByIdTest() {
        val tvShowsList  = listOf(createTVShow(550), createTVShow(432), createTVShow(443))
        runBlocking {
            tvShowsDatabase.tvShowsDao().insertTVShows(tvShowsList)
            val tvShows =  tvShowsDatabase.tvShowsDao().loadTVShowById(432)
            assertThat(tvShows.getOrAwaitValue()?.id, `is`(432))
        }
    }

    @Test
    fun deleteTVShowTest() {
        val tvShowsList  = listOf(createTVShow(550), createTVShow(432), createTVShow(443))
        runBlocking {
            tvShowsDatabase.tvShowsDao().insertTVShows(tvShowsList)
            tvShowsDatabase.tvShowsDao().deleteTVShow(550)

            val tvShows =  tvShowsDatabase.tvShowsDao().loadTVShowById(550)
            assert(tvShows.getOrAwaitValue() == null)
        }
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