package org.techtown.diffuser.model

data class DetailTopModel(
    val title: String?,
    val overview: String,
    val postUrl: String,
    val backDropUrl: String?,
    val isFailure: Boolean = false,
    val isLoading: Boolean = false,
    val id : Long
)