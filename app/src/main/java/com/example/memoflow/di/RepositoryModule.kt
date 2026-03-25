package com.example.memoflow.di

import com.example.memoflow.data.repository.DocumentRepositoryImpl
import com.example.memoflow.domain.repository.DocumentRepository
import com.example.memoflow.domain.work.DocumentAiWorkScheduler
import com.example.memoflow.domain.work.DocumentAiWorkSchedulerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindDocumentRepository(
        impl: DocumentRepositoryImpl
    ): DocumentRepository

    @Binds
    abstract fun bindDocumentAiWorkScheduler(
        impl: DocumentAiWorkSchedulerImpl
    ): DocumentAiWorkScheduler
}