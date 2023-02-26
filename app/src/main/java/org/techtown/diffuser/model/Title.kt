package org.techtown.diffuser.model

data class Title(
    val titleM: String,
    override val viewType: Int,
    override val id: Long
) : ItemModel(id, viewType) {
}