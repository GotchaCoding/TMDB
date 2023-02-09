package org.techtown.diffuser.response


import com.google.gson.annotations.SerializedName

data class DatesNowPlaing(
    @SerializedName("maximum")
    val maximum: String = "",
    @SerializedName("minimum")
    val minimum: String = ""
)