package org.techtown.diffuser.response.search


import com.google.gson.annotations.SerializedName
import org.techtown.diffuser.BaseResponse

data class SearchResponse(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val results: List<Result> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
) : BaseResponse()