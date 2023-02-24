package org.techtown.diffuser.model

data class WrappingModel(
    val isLoading: Boolean,
    val model: HorizontalMovieModel?,
    override val viewType: Int,
    val isFailure: Boolean = false
) : ItemModel(viewType)