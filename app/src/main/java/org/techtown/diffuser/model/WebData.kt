package org.techtown.diffuser.model

data class WebData(val key : String? = "", val type: String?, override val id: Long, override val viewType: Int): ItemModel(id, viewType)