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
    @Query("Select * From RecodedWord ORDER BY iddao DESC LIMIT 5")
    fun getRecentWords(): Flow<List<Word>>

    @Delete
    suspend fun deleteSelectedWord(word: Word)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM RecodedWord")
    suspend fun deleteAllData()

    @Update
    suspend fun updateWord(word: Word)
}