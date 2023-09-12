package org.techtown.diffuser.fragment

import android.view.View
import org.techtown.diffuser.room.Word

interface ItemClickListenerWord{
    fun onItemClickWord(view: View, word: Word)
}