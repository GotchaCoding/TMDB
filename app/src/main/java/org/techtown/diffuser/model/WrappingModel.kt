package org.techtown.diffuser.model

class WrappingModel(
    val isLoading: Boolean,
    val model: HorizontalMovieModel?,
    override val viewType: Int
) : ItemModel(viewType)