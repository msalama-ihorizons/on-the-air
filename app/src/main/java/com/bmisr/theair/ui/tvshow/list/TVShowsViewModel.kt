package com.bmisr.theair.ui.tvshow.list

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bmisr.theair.repository.TVShowsRepository

class TVShowsViewModel @ViewModelInject constructor(
    private val tvShowsRepository: TVShowsRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val tvShowsListLiveData = tvShowsRepository.loadLatestTVShows()

    val favTvShowsListLiveData = tvShowsRepository.loadFavouriteTVShows()

}