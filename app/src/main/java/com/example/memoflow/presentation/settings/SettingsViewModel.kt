package com.example.memoflow.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoflow.domain.settings.AiMode
import com.example.memoflow.domain.settings.Settings
import com.example.memoflow.domain.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {

    val uiState: StateFlow<Settings> =
        settingsRepository.settingsFlow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Settings()
        )

    fun updateAiMode(aiMode: AiMode){
        viewModelScope.launch {
            settingsRepository.updateAiMode(aiMode)
        }
    }

    fun updateWifiOnly(enabled: Boolean){
        viewModelScope.launch {
            settingsRepository.updateWifiOnly(enabled)
        }
    }
}