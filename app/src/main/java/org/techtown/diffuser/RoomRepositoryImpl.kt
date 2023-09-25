package org.techtown.diffuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import org.techtown.diffuser.room.WordDao
import org.techtown.diffuser.room.WordDaoModel
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(private val wordDao: WordDao) : RoomRepository {
    override val recentWords: LiveData<List<WordDaoModel>> = wordDao.getRecentWords().asLiveData()

    override suspend fun insert(word: WordDaoModel) = wordDao.insert(word)
    override suspend fun delete() = wordDao.deleteAllData()
    override suspend fun update(word: WordDaoModel) = wordDao.updateWord(word)
    override suspend fun deleteWord(word: WordDaoModel) = wordDao.deleteSelectedWord(word)


}