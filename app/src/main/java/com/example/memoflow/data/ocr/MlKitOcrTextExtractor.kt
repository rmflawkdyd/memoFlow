package com.example.memoflow.data.ocr

import android.content.Context
import android.net.Uri
import com.example.memoflow.domain.ocr.OcrTextExtractor
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MlKitOcrTextExtractor @Inject constructor(
    @ApplicationContext private val context: Context
) : OcrTextExtractor{
    override suspend fun extractText(imagePath: String): String = suspendCancellableCoroutine { cont ->
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val image = InputImage.fromFilePath(context, Uri.parse(imagePath))

        recognizer.process(image)
            .addOnSuccessListener { result ->
                cont.resume(result.text)
            }
            .addOnFailureListener { e ->
                cont.resumeWithException(e)
            }
    }
}