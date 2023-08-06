package org.techtown.diffuser.fragment

import android.view.View
import org.techtown.diffuser.fragment.home.TheMore
import org.techtown.diffuser.model.Movie

interface ItemClickListener {
    fun onItemClick(view: View, viewType: Int, movie: Movie?, theMore: TheMore?)
}