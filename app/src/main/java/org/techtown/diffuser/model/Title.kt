package org.techtown.diffuser.model

class Title(
    val titleM: String,
    override val viewType: Int
) : ItemModel(viewType) {
}