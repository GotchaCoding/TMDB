package org.techtown.diffuser

import com.google.gson.annotations.SerializedName

open class BaseResponse(  //Retrofit Reseponse를 같은 타입으로 묶어주기위해 BaseResponse를 상속.  모든 응답클래스가 BaseResponse를 상속.
    @SerializedName("status_code")    // 레트로핏 응답 코드 넘버 활용시 사용
    val statusCode: Int? = null,
    @SerializedName("status_message")   //레트로핏 응답 메세지 스트링 활용시 사.
    val statusMessage: String? = null,
)
