package org.techtown.diffuser.response


import com.google.gson.annotations.SerializedName

data class ProductionCountry_3(
    @SerializedName("iso_3166_1")
    val iso31661: String = "",
    @SerializedName("name")
    val name: String = ""
)