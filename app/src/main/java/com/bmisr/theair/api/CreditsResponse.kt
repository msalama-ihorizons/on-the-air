package com.tiendito.bmisrmovies.api

import com.google.gson.annotations.SerializedName

data class CreditsResponse (@SerializedName("cast") val cast: List<Cast>)

data class Cast(
    @SerializedName("character") val character: String,
    @SerializedName("name") val name: String,
    @SerializedName("profile_path") val profilePath: String
)
