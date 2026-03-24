package com.example.memoflow.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.memoflow.data.local.converter.DocumentConverters
import com.example.memoflow.data.local.dao.DocumentDao
import com.example.memoflow.data.local.entity.DocumentEntity

@Database(
    entities = [DocumentEntity::class],
    version = 1,
    exportSchema = false)


@TypeConverters(DocumentConverters::class)
abstract class MemoFlowDatabase : RoomDatabase() {
    abstract fun documentDao(): DocumentDao
}


