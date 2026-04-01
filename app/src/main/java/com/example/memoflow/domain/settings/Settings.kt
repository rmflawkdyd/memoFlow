package com.example.memoflow.domain.settings

enum class AiMode{
    AUTO,
    ON_DEVICE_FIRST,
    CLOUD_FIRST
}

enum class OcrLanguage{
    AUTO,
    KOREAN,
    JAPANESE,
    CHINESE,
    LATIN
}

data class Settings(
    val aiMode: AiMode = AiMode.AUTO,
    val wifiOnly: Boolean = false,
    val ocrLanguage: OcrLanguage = OcrLanguage.AUTO
)