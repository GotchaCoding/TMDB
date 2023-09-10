package org.techtown.diffuser.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RecodedWord")
data class Word(@PrimaryKey @ColumnInfo("word") val word: String)