package org.techtown.diffuser.model

import org.techtown.diffuser.fragment.home.TheMore

data class TitleModel(
    val title: String,
    val theMore: TheMore? = null,
    override val viewType: Int,
    override val id: Long
) : ItemModel(id, viewType)