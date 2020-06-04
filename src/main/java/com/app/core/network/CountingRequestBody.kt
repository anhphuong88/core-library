package com.app.core.network

import android.util.Log
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*
import okio.Okio.buffer
import java.io.IOException



/**
 * Decorates an OkHttp request body modelTo count the number of bytes written when writing it. Can
 * decorate any request body, but is most useful for tracking the upload progress of large
 * multipart requests.
 */
internal class CountingRequestBody(
    var requestBody: RequestBody,
    var listener: ((bytesWritten: Long, contentLength: Long) -> Unit)?
) : RequestBody() {

    private var countingSink: CountingSink? = null

    override fun contentType(): MediaType? {
        return requestBody.contentType()
    }

    override fun contentLength(): Long {
        try {
            return requestBody.contentLength()
        } catch (e: IOException) {
            Log.e(this::class.java.name, e.message ?: "IOException")
        }

        return -1
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
//        val progressOutputStream = ProgressOutputStream(sink.outputStream(),  object : ProgressCallback {
//            override fun onProgressChanged(numBytes: Long, totalBytes: Long, percent: Float) {
////                Logger.w("percent $percent")
//                listener?.invoke(numBytes, totalBytes)
//            }
//
//        }, contentLength())
//        val progressSink = progressOutputStream.sink().buffer()
//        requestBody.writeTo(progressSink)
//        progressSink.flush()

        countingSink = CountingSink(sink)
//        countingSink?.let { countingSink ->
//            val bufferedSink = countingSink.buffer()//?.buffer()
//            countingSink?.let {
//                requestBody.writeTo(it)
//            }
//            bufferedSink?.flush()
//        }

        countingSink?.let { countingSink ->
            val bufferedSink = Okio.buffer(countingSink)//countingSink.buffer()

            requestBody.writeTo(bufferedSink)

            bufferedSink.flush();
        }
    }

    private inner class CountingSink(delegate: Sink) : ForwardingSink(delegate) {

        private var bytesWritten: Long = 0

        @Throws(IOException::class)
        override fun write(source: Buffer, byteCount: Long) {
            super.write(source, byteCount)
            bytesWritten += byteCount
            listener?.invoke(bytesWritten, contentLength())
        }

    }
}
