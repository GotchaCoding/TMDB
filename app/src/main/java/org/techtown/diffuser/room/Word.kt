package org.techtown.diffuser.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.model.ItemModel

data class Word(
    override val id: Long,
    override val viewType: Int,
    val word: String,
) : ItemModel(id, viewType) {
    companion object {
        fun of(model: WordDaoModel): Word {
            return Word(
                id = model.id,
                viewType = Constants.VIEW_TYPE_WORD_RECORD,
                word = model.word,
            )
        }
    }
}

@Entity(tableName = "RecodedWord")
data class WordDaoModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("word") val word: String,
) {
    companion object {
        fun of(word: Word): WordDaoModel {
            return WordDaoModel(
                id = word.id,
                word = word.word
            )
        }

        fun wordForInsert(keyWord: String): WordDaoModel {
            return WordDaoModel(
                word = keyWord
            )
        }
    }
}

