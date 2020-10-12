package com.bmisr.theair.ui.moviedetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.bmisr.theair.repository.TVShowsRepository
import com.bmisr.theair.ui.tvshowdetails.TVShowDetailsActivity
import com.bmisr.theair.ui.tvshowdetails.TVShowDetailsActivity.Companion.EXTRA_TV_SHOW_ID
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by Mohamed Salama on 10/12/2020.
 */
class TVShowDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository = Mockito.mock(TVShowsRepository::class.java)
    private val savedStateHandle = Mockito.mock(SavedStateHandle::class.java)

    @Test
    fun testTVShowDetails() {
        savedStateHandle.set(EXTRA_TV_SHOW_ID, "")
        Mockito.verify(repository, Mockito.never())
            .loadTVShowDetails(332)
    }

    @Test
    fun testTVShowCast() {
        savedStateHandle.set(EXTRA_TV_SHOW_ID, "")
        Mockito.verify(repository, Mockito.never())
            .loadTVShowCast(332)
    }

    @Test
    fun testSimilarTVShow() {
        savedStateHandle.set(EXTRA_TV_SHOW_ID, "")
        Mockito.verify(repository, Mockito.never())
            .loadSimilarTVShows(332)
    }
}