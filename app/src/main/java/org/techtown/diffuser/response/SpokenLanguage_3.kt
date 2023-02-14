package org.techtown.diffuser.response


import com.google.gson.annotations.SerializedName

data class SpokenLanguage_3(
    @SerializedName("english_name")
    val englishName: String = "",
    @SerializedName("iso_639_1")
    val iso6391: String = "",
    @SerializedName("name")
    val name: String = ""
)