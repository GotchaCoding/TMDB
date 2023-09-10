package org.techtown.diffuser

import androidx.lifecycle.LiveData
import org.techtown.diffuser.room.Word

interface RepositoryRoom {

    val allWord: LiveData<List<Word>>
    suspend fun insert(word: Word)
}

