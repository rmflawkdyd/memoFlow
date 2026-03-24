package com.example.memoflow.di

import com.example.memoflow.domain.work.DocumentAiWorkScheduler
import com.example.memoflow.domain.work.DocumentAiWorkSchedulerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkerModule {
    @Binds
    abstract fun bindDocumentAiWorkScheduler(
        impl: DocumentAiWorkSchedulerImpl
    ): DocumentAiWorkScheduler
}