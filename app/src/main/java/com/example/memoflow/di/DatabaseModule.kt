package com.example.memoflow.di

import android.content.Context
import androidx.room.Room
import com.example.memoflow.data.local.dao.DocumentDao
import com.example.memoflow.data.local.db.MemoFlowDatabase
import com.example.memoflow.data.repository.DocumentRepositoryImpl
import com.example.memoflow.domain.repository.DocumentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MemoFlowDatabase{
        return Room.databaseBuilder(
            context ,
            MemoFlowDatabase::class.java ,
            "memo_flow_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDocumentDao(
        database: MemoFlowDatabase
    ): DocumentDao{
        return database.documentDao()
    }

}