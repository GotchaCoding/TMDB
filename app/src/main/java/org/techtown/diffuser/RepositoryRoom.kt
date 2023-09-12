package org.techtown.diffuser

import androidx.lifecycle.LiveData
import org.techtown.diffuser.room.Word

interface RepositoryRoom {

    val recentWords: LiveData<List<Word>>
    suspend fun insert(word: Word)

    suspend fun delete()

    suspend fun update(word: Word)

    suspend fun deleteWord(word: Word)
}

