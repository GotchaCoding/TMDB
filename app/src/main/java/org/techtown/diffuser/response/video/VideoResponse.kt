package org.techtown.diffuser.response.video

import org.techtown.diffuser.BaseResponse

data class VideoResponse(
    val id: Int,
    val results: List<Result>
) : BaseResponse()