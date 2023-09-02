package org.techtown.diffuser.response.video

import com.google.gson.annotations.SerializedName
import org.techtown.diffuser.BaseResponse

data class VideoResponse(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("results")
    val results: List<Result> = listOf()
) : BaseResponse()