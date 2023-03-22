package org.techtown.diffuser

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow


open class BaseResponse(
    @SerializedName("status_code")
    val statusCode: Int? = null,
    @SerializedName("status_message")
    val statusMessage: String? = null,
)

interface A {
    suspend fun a() : Flow<Resource<BaseResponse>>
}