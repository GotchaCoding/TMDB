package org.techtown.diffuser.model

import org.techtown.diffuser.con.Constants.VIEW_TYPE_CAST_RV

data class CastRv(
    val imgActor : String? = "",
    val castChracter : String? = "",
    val castName : String? = "",
) : ItemModel(id = 1, viewType = VIEW_TYPE_CAST_RV)