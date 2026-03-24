package com.example.memoflow.data.local.converter

import androidx.room.TypeConverter
import com.example.memoflow.data.local.entity.DocumentStatus

class DocumentConverters {

    @TypeConverter
    fun fromDocumentStatus(status: DocumentStatus):String{
        return status.name
    }

    @TypeConverter
    fun toDocumentStatus(value:String): DocumentStatus{
        return DocumentStatus.valueOf(value)
    }
}