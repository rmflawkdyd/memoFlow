package com.example.memoflow.domain.settings

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val settingsFlow: Flow<Settings>
    suspend fun updateAiMode(aiMode: AiMode)
    suspend fun updateWifiOnly(enabled: Boolean)
    suspend fun updateOcrLanguage(language: OcrLanguage)
}


