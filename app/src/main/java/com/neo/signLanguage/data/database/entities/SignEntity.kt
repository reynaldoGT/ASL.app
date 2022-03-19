package com.neo.signLanguage.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.neo.signLanguage.data.models.SingDbModel


@Entity(tableName = "sign_table")
data class SignEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "sing") val sing: String
)

fun SingDbModel.toDatabase() = SignEntity(id = id, sing = sing)

