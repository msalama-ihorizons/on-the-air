package com.bmisr.theair.api.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Mohamed Salama on 10/12/2020.
 */
data class RateResponse(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String,
    @SerializedName("success") val success: Boolean
)