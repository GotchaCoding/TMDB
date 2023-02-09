package org.techtown.diffuser.model

class WrappingModel(
    val isLoading: Boolean,
    val model: HorizontalMoviesModel?,
    override val viewType: Int
) : ItemModel(viewType)