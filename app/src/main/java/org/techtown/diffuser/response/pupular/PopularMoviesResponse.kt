package org.techtown.diffuser.response.pupular


import com.google.gson.annotations.SerializedName
import org.techtown.diffuser.BaseResponse

data class PopularMoviesResponse(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val results: List<ResultPopular> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
) : BaseResponse()