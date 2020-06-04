@file:Suppress("DEPRECATION")

package com.app.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.app.core.log.Logger
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.lang.ref.WeakReference
import java.net.SocketTimeoutException

internal fun isValid(str: String?): Boolean {
    if (!str.isNullOrEmpty()) {
        try {
            JSONObject(str);
        } catch (e: JSONException) {
            try {
                JSONArray(str)
            } catch (e: JSONException) {
                return false
            }
        }
        return true
    }
    return false
}

internal  fun <T> modelTo(model: T?, gson: Gson = GsonBuilder().disableHtmlEscaping().create()): String? = model?.let { gson.toJson(model) }

internal class NetClient(private var client: OkHttpClient?, private var context: WeakReference<Context?>?) {
    fun close() {
        client = null
    }

    private fun isAvailable(): Boolean {
        context?.let {
            val cm = it.get()?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            return cm?.run {
                val info: NetworkInfo? = this.activeNetworkInfo
                info?.isConnectedOrConnecting

            } ?: false
        }
        return false
    }

    private fun query(url: String?, paths: String?, params: Map<String, String?>?): String? {
        return url?.let {
            var link: String = it

            val httpUrl: HttpUrl? = HttpUrl.parse(link)
            httpUrl?.let { hUrl ->
                val builder: HttpUrl.Builder = hUrl.newBuilder()
                if (paths?.isNotEmpty() == true) builder.addPathSegments(paths)
                params?.let { par ->
                    par.forEach { entry ->
                        builder.addQueryParameter(entry.key, entry.value);
                    }
                }
                link = builder.build().toString()
            }
            link
        }
    }

    private fun queryMultiPart(fileKey: String, filePath: String, fileName: String? = null, fileType: String? = null, formData: Map<String, String>? = null): RequestBody {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        formData?.forEach { entry ->
            builder.addFormDataPart(entry.key, entry.value)
        }
        val files = filePath.split(",")
        if (files.size == 1) {
            val file = File(filePath)
            if (file.exists()) {
                builder.addFormDataPart(
                    fileKey,
                    fileName ?: file.name,
                    RequestBody.create(MediaType.parse(fileType ?: "File/*"), file)
                )
            }
//            formBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
        } else if (files.size > 1){
            for (path in files) {
                val file = File(path)
                if (file.exists()) {
                    builder.addFormDataPart(
                        fileKey,
                        file.name,
                        RequestBody.create(MediaType.parse(fileType ?: "File/*"), file)
                    )
                }
            }
        }
        return builder.build()
    }

    private fun call(request: Request, callBack: (net: NetResponse<String>) -> Unit): Call? {
        val c: Call? = client?.newCall(request)
        val net: NetResponse<String> = NetResponseImpl()
        c?.let { call ->
            var res: Response? = null
            var body: ResponseBody? = null
            try {
                res = call.execute()
                body = res.body()
                net.code(res.code())
                body?.let { b ->
                    net.message("")
                    try {
                        val str: String = b.string()
//                        Logger.w("ket qua tra ve : ${str}")
                        net.result(str)
                    } catch (e: IOException) {
                        net.message("")
                        net.result("")
                    }
                }
                if (!res.isSuccessful) {
                    if (res.code() == 500) net.result("")
                    net.message(res.message())
                    val restNet: Response? = res.networkResponse()
                    restNet?.let { r ->
                        net.code(r.code())
                        net.message(r.message())

                    }
                } else Unit
            } catch (e: SocketTimeoutException) {
                net.code(if (isAvailable()) -100 else -102)
                net.message("Network Socket is unreachable  ${e.message}")
                net.result("")
            } catch (e: IOException) {
                net.code(if (isAvailable()) -101 else -102)
                net.message("Network IO is unreachable ${e.message}")
                net.result("")
            } finally {
                body?.close()
                res?.close()
                call.cancel();
                callBack(net)
            }
        }
        return c
    }

    fun get(
        url: String?,
        paths: String?,
        params: Map<String, String?>?,
        headers: Headers?,
        callBack: (net: NetResponse<String>) -> Unit
    ): Call? {
        return query(url, paths, params)?.run {
            var builder: Request.Builder = Request.Builder()
            headers?.let {
                builder = builder.headers(headers)
            }
            val request: Request = builder.url(this).build()
            call(request, callBack)
        }
    }

    fun upload(
        url: String?,
        fileKey: String,
        filePath: String,
        fileName: String?,
        fileType: String? = null,
        formData: Map<String, String>?,
        paths: String?,
        params: Map<String, String?>?,
        headers: Headers?,
        listener: ((bytesWritten: Long, contentLength: Long) -> Unit)?,
        callBack: (net: NetResponse<String>) -> Unit
    ): Call? {
        return query(url, paths, params)?.run {
            val requestBody = queryMultiPart(fileKey, filePath, fileName, fileType, formData)
            val countingRequestBody = CountingRequestBody(requestBody, listener)
            val builder: Request.Builder = Request.Builder()
            headers?.let { builder.headers(headers) }
            val request: Request = builder.url(this).post(countingRequestBody).build()
            call(request, callBack)
        }
    }

    fun post(
        url: String?,
        paths: String?,
        json: String?,
        params: Map<String, String?>?,
        headers: Headers?,
        callBack: (net: NetResponse<String>) -> Unit
    ): Call? {
        return query(url, paths, params)?.run {
            var builder: Request.Builder = Request.Builder()
            headers?.let {
                builder = builder.headers(headers)
            }
            var formBody: RequestBody = FormBody.Builder().build();
            json?.let {
                Logger.w("post body: $json")
                if (isValid(it))
                    formBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
            }
            val request: Request = builder.url(this).post(formBody).build()
            call(request, callBack)
        }
    }

    fun delete(
        url: String?,
        paths: String?,
        json: String?,
        params: Map<String, String?>?,
        headers: Headers?,
        callBack: (net: NetResponse<String>) -> Unit
    ): Call? {
        return query(url, paths, params)?.run {
            var builder: Request.Builder = Request.Builder()
            headers?.let {
                builder = builder.headers(headers)
            }
            var formBody: RequestBody = FormBody.Builder().build();
            json?.let {
                Logger.w("delete body: $json")
                if (isValid(it))
                    formBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
            }
            val request: Request = builder.url(this).delete(formBody).build()
            call(request, callBack)
        }
    }

    fun patch(
        url: String?,
        paths: String?,
        json: String?,
        params: Map<String, String?>?,
        headers: Headers?,
        callBack: (net: NetResponse<String>) -> Unit
    ): Call? {
        return query(url, paths, params)?.run {
            var builder: Request.Builder = Request.Builder()
            headers?.let {
                builder = builder.headers(headers)
            }
            var formBody: RequestBody = FormBody.Builder().build();
            json?.let {
                Logger.w("patch body: $json")
                if (isValid(it))
                    formBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
            }
            val request: Request = builder.url(this).patch(formBody).build()
            call(request, callBack)
        }
    }

    fun put(
        url: String?,
        paths: String?,
        json: String?,
        params: Map<String, String?>?,
        headers: Headers?,
        callBack: (net: NetResponse<String>) -> Unit
    ): Call? {
        return query(url, paths, params)?.run {
            var builder: Request.Builder = Request.Builder()
            headers?.let {
                builder = builder.headers(headers)
            }
            var formBody: RequestBody = FormBody.Builder().build();
            json?.let {
                Logger.w("put body: $json")
                if (isValid(it))
                    formBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
            }
            val request: Request = builder.url(this).put(formBody).build()
            call(request, callBack)
        }
    }

    fun head(
        url: String?,
        paths: String?,
        params: Map<String, String?>?,
        headers: Headers?,
        callBack: (net: NetResponse<String>) -> Unit
    ): Call? {
        return query(url, paths, params)?.run {
            var builder: Request.Builder = Request.Builder()
            headers?.let {
                builder = builder.headers(headers)
            }
            val request: Request = builder.url(this).head().build()
            call(request, callBack)
        }
    }
}
