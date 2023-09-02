package org.techtown.diffuser.response.video

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("iso_3166_1")
    val iso31661: String = "",
    @SerializedName("iso_639_1")
    val iso6391: String = "",
    @SerializedName("key")
    val key: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("official")
    val official: Boolean = false,
    @SerializedName("published_at")
    val publishedat: String = "",
    @SerializedName("site")
    val site: String = "",
    @SerializedName("size")
    val size: Int = 0,
    @SerializedName("type")
    val type: String = ""
)
