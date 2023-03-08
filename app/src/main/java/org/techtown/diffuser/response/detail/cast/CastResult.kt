package org.techtown.diffuser.response.detail.cast


import com.google.gson.annotations.SerializedName
import org.techtown.diffuser.BaseResponse

data class CastResult(
    @SerializedName("cast")
    val cast: List<Cast> = listOf(),
    @SerializedName("crew")
    val crew: List<Crew> = listOf(),
    @SerializedName("id")
    val id: Int = 0
): BaseResponse()