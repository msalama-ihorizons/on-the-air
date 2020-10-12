package com.bmisr.theair.api

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class TVShowsResponse(@SerializedName("results") val tvShows: List<TVShow>)

data class Genre(val id: Int, val name: String)
data class Network(
    val id: Int,
    val name: String,
    @SerializedName("logo_path") val logoPath: String
)

@Entity(tableName = "tv_shows_table")
data class TVShow(
    @PrimaryKey val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("first_air_date") val firstAirDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("episode_number") val episodeNumber: Int,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("overview") val overview: String,
    @SerializedName("homepage") val homepage: String?,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("networks") val networks: List<Network>

)