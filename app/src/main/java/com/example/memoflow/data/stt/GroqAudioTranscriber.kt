package com.example.memoflow.data.stt

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.example.memoflow.domain.stt.AudioTranscriber
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class GroqAudioTranscriber @Inject constructor(
    @ApplicationContext private val context: Context,
    private val groqSttApi: GroqSttApi
) : AudioTranscriber{
    override suspend fun transcribe(audioPath: String): String {
        val uri = Uri.parse(audioPath)
        val bytes = context.contentResolver.openInputStream(uri)?.use {it.readBytes() }?:error("오디오 파일을 읽을 수 없습니다.")

        val mimeType = context.contentResolver.getType(uri)?:"audio/*"
        val fileName = resolveDisplayName(context.contentResolver,uri)?:"audio.m4a"

        val fileBody = bytes.toRequestBody(mimeType.toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("file",fileName,fileBody)

        return groqSttApi.transcribe(
            file = filePart,
            model = "whisper-large-v3-turbo".toRequestBody("text/plain".toMediaType()),
            language = "ko".toRequestBody("text/plain".toMediaType()),
            responseFormat = "json".toRequestBody("text/plain".toMediaType())
        ).text
    }

    private fun resolveDisplayName(
        contentResolver: ContentResolver,
        uri: Uri
    ): String?{
        if(uri.scheme!=ContentResolver.SCHEME_CONTENT) return uri.lastPathSegment
        contentResolver.query(uri,arrayOf(OpenableColumns.DISPLAY_NAME),null,null)
            ?.use { cursor ->
                val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index >= 0 && cursor.moveToFirst()) return cursor.getString(index)
            }
        return null
    }

}