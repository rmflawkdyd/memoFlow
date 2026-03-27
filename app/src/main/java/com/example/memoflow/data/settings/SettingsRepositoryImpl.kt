package com.example.memoflow.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.memoflow.domain.settings.AiMode
import com.example.memoflow.domain.settings.Settings
import com.example.memoflow.domain.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): SettingsRepository {
    override val settingsFlow: Flow<Settings> =
        dataStore.data.map { prefs->
            Settings(
                aiMode = prefs[KEY_AI_MODE]?.let { AiMode.valueOf(it) } ?: AiMode.AUTO,
                wifiOnly = prefs[KEY_WIFI_ONLY]?: false
            )
        }

    override suspend fun updateAiMode(aiMode: AiMode) {
        dataStore.edit { prefs->
            prefs[KEY_AI_MODE] = aiMode.name
        }
    }

    override suspend fun updateWifiOnly(enabled: Boolean) {
        dataStore.edit { prefs->
            prefs[KEY_WIFI_ONLY] = enabled
        }
    }

    companion object {
        private val KEY_AI_MODE = stringPreferencesKey("ai_mode")
        private val KEY_WIFI_ONLY = booleanPreferencesKey("wifi_only")
    }
}