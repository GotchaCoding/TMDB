package org.techtown.diffuser.listener

import android.view.View
import org.techtown.diffuser.model.Movie

interface PopularClickListener {
    fun onClick(movie: Movie)
}