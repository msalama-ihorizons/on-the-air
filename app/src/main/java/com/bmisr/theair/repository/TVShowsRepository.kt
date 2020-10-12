package com.bmisr.theair.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import com.bmisr.theair.api.response.TVShow
import com.bmisr.theair.api.TVShowsApis
import com.bmisr.theair.db.TVShowsDao
import com.bmisr.theair.model.Resource
import com.bmisr.theair.utils.Constants.API_KEY
import com.bmisr.theair.api.response.Cast
import com.bmisr.theair.api.request.RateRequest
import com.bmisr.theair.api.response.RateResponse
import javax.inject.Inject

class TVShowsRepository @Inject constructor(
    private val tvShowsApis: TVShowsApis,
    private val sessionRepository: SessionRepository,
    private val tvShowsDao: TVShowsDao

) {

    fun loadLatestTVShows(): LiveData<Resource<List<TVShow>>> {

        return liveData {
            emit(Resource.loading(null))

            try {
                val result = tvShowsApis.getLatestTVShows(apiKey = API_KEY)

                emit(Resource.complete(null))

                if (result.isSuccessful)
                    emit(Resource.success(result.body()?.tvShows?.sortedBy { it.voteAverage }))
                else
                    emit(Resource.error(result.message(), null))

            } catch (e: Exception) {
                emit(Resource.complete(null))
                emit(Resource.error(e.message, null))
            }

        }
    }

    fun loadSimilarTVShows(tvShowId: Int): LiveData<Resource<List<TVShow>>> {

        return liveData {
            emit(Resource.loading(null))

            try {
                val result = tvShowsApis.getSimilarTVShows(tvShowId = tvShowId, apiKey = API_KEY)

                emit(Resource.complete(null))

                if (result.isSuccessful)
                    emit(Resource.success(result.body()?.tvShows))
                else
                    emit(Resource.error(result.message(), null))

            } catch (e: Exception) {
                emit(Resource.complete(null))
                emit(Resource.error(e.message, null))
            }


        }
    }

    fun loadTVShowDetails(tvShowId: Int): LiveData<Resource<TVShow>> {

        return liveData {

            emit(Resource.loading(null))

            try {
                val result = tvShowsApis.getTVShowDetails(tvShowId = tvShowId, apiKey = API_KEY)

                emit(Resource.complete(null))

                if (result.isSuccessful)
                    emit(Resource.success(result.body()))
                else
                    emit(Resource.error(result.message(), null))

            } catch (e: Exception) {
                emit(Resource.complete(null))
                emit(Resource.error(e.message, null))
            }


        }
    }

    fun loadTVShowCast(tvShowId: Int): LiveData<Resource<List<Cast>>> {

        return liveData {

            emit(Resource.loading(null))

            try {
                val result = tvShowsApis.getTvShowCast(tvShowId = tvShowId, apiKey = API_KEY)

                emit(Resource.complete(null))

                if (result.isSuccessful)
                    emit(Resource.success(result.body()?.cast))
                else
                    emit(Resource.error(result.message(), null))
            }catch (e: Exception) {
                emit(Resource.complete(null))
                emit(Resource.error(e.message, null))
            }


        }
    }

    fun rateTVShow(tvShowId: Int?, ratingValue: Float): LiveData<Resource<RateResponse>> {
        return liveData {

            emit(Resource.loading(null))

            if (sessionRepository.isExpired()) {
                val sessionResult = tvShowsApis.generateGuestSession(apiKey = API_KEY)
                if (sessionResult.isSuccessful)
                    sessionRepository.saveGuestSession(sessionResult.body()?.guestSessionId)
            }

            try {

                val result = tvShowsApis.rateTVShow(
                    tvShowId = tvShowId ?: -1,
                    apiKey = API_KEY,
                    guestSessionId = sessionRepository.getGuestSession() ?: "",
                    rateRequest = RateRequest(
                        ratingValue
                    )
                )

                if (result.isSuccessful)
                    emit(Resource.success(result.body()))
                else
                    emit(Resource.error(result.message(), null))

                emit(Resource.complete(null))


            } catch (e: Exception) {
                emit(Resource.error(e.message, null))
                emit(Resource.complete(null))
            }

        }
    }

    suspend fun addFavouriteTVShow(isFavourite: Boolean, tvShow: TVShow?) {

        if (isFavourite)
            tvShowsDao.insert(tvShow)
        else
            tvShowsDao.deleteTVShow(tvShow?.id)
    }

    fun isFavouriteTVShow(tvShowId: Int): LiveData<Boolean> {

        val resultLiveData = MediatorLiveData<Boolean>()

        resultLiveData.addSource(tvShowsDao.loadTVShowById(tvShowId)) { tvShow ->
            tvShow?.let {
                resultLiveData.value = true
                return@addSource
            }
            resultLiveData.value = false

        }

        return resultLiveData
    }

    fun loadFavouriteTVShows(): LiveData<Resource<List<TVShow>>> {
        val resultLiveData = MediatorLiveData<Resource<List<TVShow>>>()
        resultLiveData.value  = Resource.loading(null)

        resultLiveData.addSource(tvShowsDao.loadTVShows()) {
            tvShows->
            resultLiveData.value = Resource.complete(null)

            resultLiveData.value = Resource.success(tvShows)
        }
        return resultLiveData
    }
}