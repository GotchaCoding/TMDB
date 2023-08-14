package org.techtown.diffuser.model

data class WrappingDetailModel(
    val isLoading: Boolean,
    val castModel: HorizontalCastModel?,
    val detailTopModel: DetailTopModel?,
    val webModel: WebModel?,
    override val viewType: Int,
    val isFailure: Boolean,
    override val id: Long
) : ItemModel(id, viewType)
