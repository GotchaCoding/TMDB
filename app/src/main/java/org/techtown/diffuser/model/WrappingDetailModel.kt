package org.techtown.diffuser.model

class WrappingDetailModel(
    val isLoading: Boolean,
    val castModel: HorizontalCastModel?,
    val detailTopModel: DetailTopModel?,
    override val viewType: Int,
    val isFailure : Boolean
) : ItemModel(viewType)
