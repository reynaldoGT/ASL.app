package com.neo.signLanguage.data.database.entities.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.neo.signLanguage.data.database.entities.SignEntity
@Dao
interface SignDao {
    @Query("SELECT * FROM sign_table")
    suspend fun getAllSing(): List<SignEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sing: List<SignEntity>)

    @Insert
    fun addSing(SignEntity: SignEntity): Long
}