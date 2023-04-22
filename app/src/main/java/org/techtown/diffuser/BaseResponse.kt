package org.techtown.diffuser

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("status_code")
    val statusCode: Int? = null,
    @SerializedName("status_message")
    val statusMessage: String? = null,
)
