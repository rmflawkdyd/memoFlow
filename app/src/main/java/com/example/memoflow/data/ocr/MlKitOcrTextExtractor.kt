package com.example.memoflow.data.ocr

import android.content.Context
import android.net.Uri
import com.example.memoflow.domain.ocr.OcrTextExtractor
import com.example.memoflow.domain.settings.OcrLanguage
import com.example.memoflow.domain.settings.SettingsRepository
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.chinese.ChineseTextRecognizerOptions
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MlKitOcrTextExtractor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsRepository: SettingsRepository
) : OcrTextExtractor{
    override suspend fun extractText(imagePath: String): String  {
        val settings = settingsRepository.settingsFlow.first()
        val targetLanguage = resolveOcrLanguage(settings.ocrLanguage)
        val recognizer = createRecognizer(targetLanguage)
        val image = InputImage.fromFilePath(context, Uri.parse(imagePath))

        return suspendCancellableCoroutine { cont ->
            recognizer.process(image)
                .addOnSuccessListener { result ->
                    cont.resume(result.text)
                }
                .addOnFailureListener { e ->
                    cont.resumeWithException(e)
                }
        }

    }

    private fun resolveOcrLanguage(saved: OcrLanguage): OcrLanguage{
        if(saved!= OcrLanguage.AUTO) return  saved

        return when(Locale.getDefault().language){
            "ko" -> OcrLanguage.KOREAN
            "ja" -> OcrLanguage.JAPANESE
            "zh" -> OcrLanguage.CHINESE
            else -> OcrLanguage.LATIN
        }
    }

    private fun createRecognizer(language: OcrLanguage) = when (language) {
        OcrLanguage.KOREAN ->
            TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())

        OcrLanguage.JAPANESE ->
            TextRecognition.getClient(JapaneseTextRecognizerOptions.Builder().build())

        OcrLanguage.CHINESE ->
            TextRecognition.getClient(ChineseTextRecognizerOptions.Builder().build())

        OcrLanguage.LATIN,
        OcrLanguage.AUTO ->
            TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    }
}