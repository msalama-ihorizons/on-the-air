package com.bmisr.theair.api.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Mohamed Salama on 10/12/2020.
 */

data class GuestSessionResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("guest_session_id")val guestSessionId: String
)