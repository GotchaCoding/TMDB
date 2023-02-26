package org.techtown.diffuser.model

data class WrappingDetailModel(
    val isLoading: Boolean,
    val castModel: HorizontalCastModel?,
    val detailTopModel: DetailTopModel?,
    override val viewType: Int,
    val isFailure: Boolean,
    override val id: Long
) : ItemModel(id, viewType)
