package com.example.memoflow.di

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.memoflow.data.ai.GroqDocumentAiProcessor
import com.example.memoflow.data.ocr.MlKitOcrTextExtractor
import com.example.memoflow.data.repository.DocumentRepositoryImpl
import com.example.memoflow.data.settings.SettingsRepositoryImpl
import com.example.memoflow.data.stt.GroqAudioTranscriber
import com.example.memoflow.domain.ai.DocumentAiProcessor
import com.example.memoflow.domain.ocr.OcrTextExtractor
import com.example.memoflow.domain.repository.DocumentRepository
import com.example.memoflow.domain.settings.SettingsRepository
import com.example.memoflow.domain.stt.AudioTranscriber
import com.example.memoflow.domain.work.DocumentAiWorkScheduler
import com.example.memoflow.domain.work.DocumentAiWorkSchedulerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

    @Binds
    abstract fun bindOcrTextExtractor(
        impl: MlKitOcrTextExtractor
    ): OcrTextExtractor

    @Binds
    abstract fun bindDocumentAiProcessor(
        impl: GroqDocumentAiProcessor
    ): DocumentAiProcessor

    @Binds
    abstract fun bindAudioTranscriber(
        impl: GroqAudioTranscriber
    ): AudioTranscriber

    @Binds
    abstract fun bindSettingsRepository(
        impl: SettingsRepositoryImpl
    ): SettingsRepository

}

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule{
    @SuppressLint("RestrictedApi")
    @Provides
    @Singleton
    fun providePreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("settings.preferences_pb") }
        )
    }
}