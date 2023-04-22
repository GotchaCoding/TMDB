package org.techtown.diffuser.model

data class HorizontalCastModel(override val id: Long, val castList: List<CastRv>, override val viewType: Int) : ItemModel(id, viewType) {  //copy 못씀

}