package org.techtown.diffuser.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.techtown.diffuser.model.ItemModel

@Entity(tableName = "RecodedWord")
data class Word(
    @PrimaryKey(autoGenerate = true) val iddao: Int = 0,
    @ColumnInfo("word") val word: String,
    override val viewType: Int,
    override val id: Long
) : ItemModel(id, viewType)

