package org.techtown.diffuser.model

import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Movie

data class DetailTopModel(
    val title: String?,
    val overview: String,
    val postUrl: String,
    val backDropUrl: String,
    val isfailure: Boolean = false,
    val isLoading: Boolean = false,
    val id : Long
)