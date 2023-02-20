package org.techtown.diffuser.model

import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Movie

class DetailTopModel(
    val title: String?,
    val overview: String,
    val postUrl: String,
    val backDropUrl: String,
    override val viewType: Int,
    val isfailure: Boolean = false,
    val isLoading: Boolean = false
) : ItemModel(viewType) {
}