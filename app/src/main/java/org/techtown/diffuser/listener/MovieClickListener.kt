package org.techtown.diffuser.listener

import android.view.View
import org.techtown.diffuser.model.Movie

fun interface MovieClickListener {
    fun onClick(view: View, viewType: Int, movie: Movie?)
}