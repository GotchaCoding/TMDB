package org.techtown.diffuser.model

 data class Movie(
    val title: String = "",
    val rank: String = "",
    val imagePoster: String = "",
    val imageDrop: String = "",
    val idNum: Int = 1,
    override val viewType: Int,
    override val id: Long
) : ItemModel(id, viewType)