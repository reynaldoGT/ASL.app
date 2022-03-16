package com.neo.signLanguage.data.database.entities

import androidx.room.Database
import androidx.room.RoomDatabase
import com.neo.signLanguage.data.database.entities.dao.SignDao

@Database(entities = [SignEntity::class], version = 1)
abstract class SingDatabase : RoomDatabase() {
    abstract fun getSignDao(): SignDao
}