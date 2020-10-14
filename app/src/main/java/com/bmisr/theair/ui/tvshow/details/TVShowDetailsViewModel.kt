package com.bmisr.theair.ui.tvshow.details

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.bmisr.theair.api.response.TVShow
import com.bmisr.theair.repository.TVShowsRepository
import com.bmisr.theair.ui.tvshow.details.TVShowDetailsActivity.Companion.EXTRA_TV_SHOW_ID
import kotlinx.coroutines.launch

class TVShowDetailsViewModel @ViewModelInject constructor(
    private val tvShowsRepository: TVShowsRepository,
    @Assisted private val savedStateHandle: SavedStateHandle?
) : ViewModel() {

    private val tvShowIDLiveData = savedStateHandle?.getLiveData<Int>(EXTRA_TV_SHOW_ID)

    private val ratingValueLiveData = MutableLiveData<Float>()

    val tvShowDetailsLiveData = tvShowIDLiveData?.switchMap { tvShowID ->
        tvShowsRepository.loadTVShowDetails(tvShowID)
    }

    val tvShowCastLiveData = tvShowIDLiveData?.switchMap { tvShowID ->
        tvShowsRepository.loadTVShowCast(tvShowID)
    }

    val similarTVShowsLiveData = tvShowIDLiveData?.switchMap { tvShowID ->
        tvShowsRepository.loadSimilarTVShows(tvShowID)
    }

    val ratingTVShowLiveData = ratingValueLiveData.switchMap { ratingValue ->
        tvShowsRepository.rateTVShow(
            tvShowId = tvShowIDLiveData?.value,
            ratingValue = ratingValue
        )
    }

    fun rateTVShow(rateValue: Float) {
        ratingValueLiveData.value = rateValue
    }

    val favouriteTVShowLiveDate = tvShowIDLiveData?.switchMap { tvShowID->
        tvShowsRepository.isFavouriteTVShow(tvShowID)
    }

    fun addToFavourites(isFavourite: Boolean, tvShow: TVShow?) {
        viewModelScope.launch {
            tvShowsRepository.addFavouriteTVShow(isFavourite, tvShow)
        }
    }
}