package com.bmisr.theair.api

import com.bmisr.theair.BuildConfig
import com.bmisr.theair.api.request.RateRequest
import com.bmisr.theair.api.response.*
import retrofit2.Response
import retrofit2.http.*

interface TVShowsApis {

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTVShows(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<TVShowsResponse>

    @GET("tv/{tv_id}")
    suspend fun getTVShowDetails(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<TVShow>

    @GET("tv/{tv_id}/credits")
    suspend fun getTvShowCast(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<CreditsResponse>

    @GET("tv/{tv_id}/recommendations")
    suspend fun getSimilarTVShows(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<TVShowsResponse>

    @GET("authentication/guest_session/new")
    suspend fun generateGuestSession(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<GuestSessionResponse>

    @POST("tv/{tv_id}/rating")
    suspend fun rateTVShow(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("guest_session_id") guestSessionId: String,
        @Body rateRequest: RateRequest

    ): Response<RateResponse>
}