package com.example.libraryhub.model

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import okio.Source
import okio.source
import java.io.InputStream

class FileRequestBody(val inputStream: InputStream, val type: String): RequestBody() {

    override fun contentType(): MediaType? {
        return "$type/*".toMediaTypeOrNull()
    }

    override fun writeTo(sink: BufferedSink) {
        var source: Source? = null
        try {
            source = inputStream!!.source()
            sink.writeAll(source)
        } catch (e: Exception) {
            source?.close()
        }
    }
}