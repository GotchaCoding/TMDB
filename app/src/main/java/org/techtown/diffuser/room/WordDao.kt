package org.techtown.diffuser.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("Select * From RecodedWord ORDER BY id DESC LIMIT 5")
    fun getRecentWords(): Flow<List<WordDaoModel>>

    @Delete
    suspend fun deleteSelectedWord(word: WordDaoModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: WordDaoModel)

    @Query("DELETE FROM RecodedWord")
    suspend fun deleteAllData()

    @Update
    suspend fun updateWord(word: WordDaoModel)
}