package com.bmisr.theair.api

import retrofit2.Response
import retrofit2.http.*

interface TVShowsApis {

    @GET("/tv/latest")
    suspend fun getLatestTVShows(@Query("api_key") apiKey: String): Response<TVShowsResponse>

    @GET("tv/{tv_id}")
    suspend fun getTVShowDetails(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Response<TVShow>

    @GET("tv/{tv_id}/credits")
    suspend fun getTvShowCast(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Response<CreditsResponse>

    @GET("/tv/{tv_id}/recommendations")
    suspend fun getSimilarTVShows(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Response<TVShowsResponse>

    @GET("authentication/guest_session/new")
    suspend fun generateGuestSession(@Query("api_key") apiKey: String): Response<GuestSessionResponse>

    @POST("tv/{tv_id}/rating")
    suspend fun rateTVShow(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String,
        @Query("guest_session_id") guestSessionId: String,
        @Body rateRequest: RateRequest

    ): Response<RateResponse>
}