package org.techtown.diffuser.response.trend


import com.google.gson.annotations.SerializedName
import org.techtown.diffuser.BaseResponse

data class TrendResponse(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val results: List<ResultTrend> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
) : BaseResponse()