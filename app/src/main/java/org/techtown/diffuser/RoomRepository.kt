package org.techtown.diffuser

import androidx.lifecycle.LiveData
import org.techtown.diffuser.room.WordDaoModel

interface RoomRepository {

    val recentWords: LiveData<List<WordDaoModel>>
    suspend fun insert(word: WordDaoModel)

    suspend fun delete()

    suspend fun update(word: WordDaoModel)

    suspend fun deleteWord(word: WordDaoModel)
}

