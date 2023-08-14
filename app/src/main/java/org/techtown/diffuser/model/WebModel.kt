package org.techtown.diffuser.model

data class WebModel(val webData:WebData?, override val id: Long, override val viewType: Int): ItemModel(id, viewType)