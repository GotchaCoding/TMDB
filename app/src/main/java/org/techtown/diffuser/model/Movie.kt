package org.techtown.diffuser.model

 data class Movie(
    val title: String = "",
    val rank: String = "",
    val imagePoster: String = "",
    val imageDrop: String = "",
    override val viewType: Int,
    override val id: Long
) : ItemModel(id, viewType)