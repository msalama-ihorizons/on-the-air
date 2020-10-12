package com.bmisr.theair.api

import com.tiendito.bmisrmovies.api.*
import retrofit2.Response
import retrofit2.http.*

interface TVShowsApis {

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTVShows(@Query("api_key") apiKey: String): Response<TVShowsResponse>

    @GET("tv/{tv_id}")
    suspend fun getTVShowDetails(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Response<TVShow>

    @GET("tv/{tv_id}/credits")
    suspend fun getTvShowCast(
        @Path("tv_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<CreditsResponse>

    @GET("/tv/{tv_id}/recommendations")
    suspend fun getSimilarMovies(
        @Path("tv_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<TVShowsResponse>

    @GET("authentication/guest_session/new")
    suspend fun generateGuestSession(@Query("api_key") apiKey: String): Response<GuestSessionResponse>

    @POST("tv/{tv_id}/rating")
    suspend fun rateMovie(
        @Path("tv_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("guest_session_id") guestSessionId: String,
        @Body rateRequest: RateRequest

    ): Response<RateResponse>
}