package org.techtown.diffuser.response.nowplaying


import com.google.gson.annotations.SerializedName

data class DatesNowPlaing(
    @SerializedName("maximum")
    val maximum: String = "",
    @SerializedName("minimum")
    val minimum: String = ""
)