package org.techtown.diffuser.model

data class WrappingSmallModel(
    val isFailure: Boolean,
    val movies: List<Movie>?,
    override val id: Long,
    override val viewType: Int
    ) : ItemModel(id, viewType)