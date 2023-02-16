package org.techtown.diffuser.model

class CastModel(
    val image: String,
    val character: String,
    val name: String,
    override val viewType: Int
) : ItemModel(viewType) {
}