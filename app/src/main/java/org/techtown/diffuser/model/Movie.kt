package org.techtown.diffuser.model

data class Movie(
    val title: String = "",
    val rank: String = "",
    val imagePoster: String? = "",
    val imageDrop: String? = "",
    val overView: String = "",
    val isCheckedMark: Boolean = false,
    override val viewType: Int,
    override val id: Long
) : ItemModel(id, viewType)