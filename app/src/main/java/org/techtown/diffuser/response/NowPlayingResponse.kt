package org.techtown.diffuser.response


import com.google.gson.annotations.SerializedName

data class NowPlayingResponse(
    @SerializedName("dates")
    val dates: DatesNowPlaing = DatesNowPlaing(),
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val results: List<ResultNowPlaying> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
)