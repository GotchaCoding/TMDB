package org.techtown.diffuser.model

data class HorizontalMovieModel(
    val movies: List<Movie>,
    override val viewType: Int,
    override val id: Long
) : ItemModel(id, viewType)


