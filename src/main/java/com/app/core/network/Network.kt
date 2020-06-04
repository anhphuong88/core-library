package com.app.core.network

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.lang.ref.WeakReference
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
object Network {
    private var applicationContext: WeakReference<Context?>? = null
    private var isDebug = false;
    private val trustAllCerts =
        arrayOf<TrustManager>(
            object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }
        )

    private fun getHostnameVerifier(): HostnameVerifier {
        return HostnameVerifier { _, _ -> true }
    }

    // Install the all-trusting trust manager
    private var sslContext =
        SSLContext.getInstance("SSL").apply {
            init(null, trustAllCerts, SecureRandom())
        }

    // Create an ssl socket factory with our all-trusting manager
    private val sslSocketFactory = sslContext.socketFactory

    private var client: OkHttpClient.Builder = OkHttpClient.Builder()
        .protocols(arrayListOf(Protocol.HTTP_1_1))
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)

    enum class Method {
        GET, POST, DELETE, PATCH, PUT, HEAD, UPLOAD
    }

    fun init(client: OkHttpClient.Builder? = null, applicationContext: Context? = null, isDebug:Boolean ) {
        this.isDebug = isDebug
        client?.let {
            if (isDebug) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                it.addInterceptor(loggingInterceptor)
            }

            Network.client = it
            Network.client.sslSocketFactory(sslSocketFactory, (trustAllCerts[0] as X509TrustManager)).hostnameVerifier(getHostnameVerifier())

        }
        applicationContext?.let {
            Network.applicationContext = WeakReference(it)
        }
    }

    fun createClient(
        timeout: Long = 30,
        retry: Int = 0,
        retryOnFailure: Boolean = false,
        cache: Cache? = null,
        authenticator: Authenticator? = null,
        pool: ConnectionPool? = null,
        vararg interceptor: Interceptor?
    ): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder().protocols(arrayListOf(Protocol.HTTP_1_1))
        builder.sslSocketFactory(sslSocketFactory, (trustAllCerts[0] as X509TrustManager)).hostnameVerifier(getHostnameVerifier())
        for (inter in interceptor) {
            inter?.let(builder::addInterceptor)
        }
        if (retry > 0) {
            builder.addInterceptor { chain ->
                val request: Request = chain.request()
                var response: Response? = null
                try {
                    response = chain.proceed(request)
                    var count = 0
                    while (response?.isSuccessful != true && count < retry) {
                        count++
                        response = chain.proceed(request)
                    }

                } catch (e: IOException) {
                    Log.e(this::class.java.name, e.message ?: "IOException")
                }
                response!!
            }
        }
        builder.retryOnConnectionFailure(retryOnFailure)
        cache?.let { builder.cache(cache) }
        authenticator?.let(builder::authenticator)
        pool?.let(builder::connectionPool)
        return builder.connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS).build();
    }

    fun get(
        url: String?,
        paths: String? = null,
        body: Any? = null,
        params: Map<String, String?>? = null,
        headers: Headers? = null,
        client: OkHttpClient? = null,
        runOn: Scheduler? = null,
        receiverOn: Scheduler? = null
    ): Subject<NetResponse<String>> {
        return call(
            url,
            paths = paths,
            body = body,
            params = params,
            headers = headers,
            client = client,
            runOn = runOn,
            receiverOn = receiverOn
        )
    }

    fun post(
        url: String?,
        paths: String? = null,
        body: Any? = null,
        params: Map<String, String?>? = null,
        headers: Headers? = null,
        client: OkHttpClient? = null,
        runOn: Scheduler? = null,
        receiverOn: Scheduler? = null
    ): Subject<NetResponse<String>> {
        return call(
            url,
            method = Method.POST,
            paths = paths,
            body = body,
            params = params,
            headers = headers,
            client = client,
            runOn = runOn,
            receiverOn = receiverOn
        )
    }

    fun head(
        url: String?,
        paths: String? = null,
        body: Any? = null,
        params: Map<String, String?>? = null,
        headers: Headers? = null,
        client: OkHttpClient? = null,
        runOn: Scheduler? = null,
        receiverOn: Scheduler? = null
    ): Subject<NetResponse<String>> {
        return call(
            url,
            method = Method.HEAD,
            paths = paths,
            body = body,
            params = params,
            headers = headers,
            client = client,
            runOn = runOn,
            receiverOn = receiverOn
        )
    }

    fun put(
        url: String?,
        paths: String? = null,
        body: Any? = null,
        params: Map<String, String?>? = null,
        headers: Headers? = null,
        client: OkHttpClient? = null,
        runOn: Scheduler? = null,
        receiverOn: Scheduler? = null
    ): Subject<NetResponse<String>> {
        return call(
            url,
            method = Method.PUT,
            paths = paths,
            body = body,
            params = params,
            headers = headers,
            client = client,
            runOn = runOn,
            receiverOn = receiverOn
        )
    }

    fun delete(
        url: String?,
        paths: String? = null,
        body: Any? = null,
        params: Map<String, String?>? = null,
        headers: Headers? = null,
        client: OkHttpClient? = null,
        runOn: Scheduler? = null,
        receiverOn: Scheduler? = null
    ): Subject<NetResponse<String>> {
        return call(
            url,
            method = Method.DELETE,
            paths = paths,
            body = body,
            params = params,
            headers = headers,
            client = client,
            runOn = runOn,
            receiverOn = receiverOn
        )
    }

    fun patch(
        url: String?,
        paths: String? = null,
        body: Any? = null,
        params: Map<String, String?>? = null,
        headers: Headers? = null,
        client: OkHttpClient? = null,
        runOn: Scheduler? = null,
        receiverOn: Scheduler? = null
    ): Subject<NetResponse<String>> {
        return call(
            url,
            method = Method.PATCH,
            paths = paths,
            body = body,
            params = params,
            headers = headers,
            client = client,
            runOn = runOn,
            receiverOn = receiverOn
        )
    }

    fun upload(
        url: String?,
        fileKey: String,
        filePath: String,
        fileName: String? = null,
        fileType: String? = null,
        formData: Map<String, String>? = null,
        listener: ((bytesWritten: Long, contentLength: Long) -> Unit)? = null,
        paths: String? = null,
        params: Map<String, String?>? = null,
        headers: Headers? = null,
        client: OkHttpClient? = null,
        runOn: Scheduler? = null,
        receiverOn: Scheduler? = null
    ): Subject<NetResponse<String>> {
        return call(
            url,
            method = Method.UPLOAD,
            paths = paths,
            body = null,
            params = params,
            headers = headers,
            client = client,
            runOn = runOn,
            receiverOn = receiverOn,
            fileKey = fileKey,
            filePath = filePath,
            fileName = fileName,
            fileType = fileType,
            formData = formData,
            listener = listener
        )
    }

    fun call(
        url: String?,
        method: Method = Method.GET,
        paths: String? = null,
        body: Any? = null,
        params: Map<String, String?>? = null,
        headers: Headers? = null,
        client: OkHttpClient? = null,
        runOn: Scheduler? = null,
        receiverOn: Scheduler? = null,
        fileKey: String? = null,
        filePath: String? = null,
        fileName: String? = null,
        fileType: String? = null,
        formData: Map<String, String>? = null,
        listener: ((bytesWritten: Long, contentLength: Long) -> Unit)? = null
    ): Subject<NetResponse<String>> {
        val sub: Subject<NetResponse<String>> = BehaviorSubject.create();
        var net: NetClient? = NetClient(client ?: Network.client.build(), applicationContext)
        val observable: Observable<NetResponse<String>> = Observable.create<NetResponse<String>> { emitter ->
            val json: String? = when (body) {
                is String? -> body
                else -> modelTo(body)
            }
            when (method) {
                Method.HEAD -> {
                    net?.head(url, paths, params, headers, callBack = {
                        if (!emitter.isDisposed)
                            emitter.onNext(it)
                        net?.close()
                        net = null
                        emitter.onComplete()
                    })
                }
                Method.PATCH -> {
                    net?.patch(url, paths, json, params, headers, callBack = {
                        if (!emitter.isDisposed)
                            emitter.onNext(it)
                        net?.close()
                        net = null
                        emitter.onComplete()
                    })
                }
                Method.DELETE -> {
                    net?.delete(url, paths, json, params, headers, callBack = {
                        if (!emitter.isDisposed)
                            emitter.onNext(it)
                        net?.close()
                        net = null
                        emitter.onComplete()
                    })
                }
                Method.POST -> {
                    net?.post(url, paths, json, params, headers, callBack = {
                        if (!emitter.isDisposed)
                            emitter.onNext(it)
                        net?.close()
                        net = null
                        emitter.onComplete()
                    })
                }
                Method.PUT -> {
                    net?.put(url, paths, json, params, headers, callBack = {
                        if (!emitter.isDisposed)
                            emitter.onNext(it)
                        net?.close()
                        net = null
                        emitter.onComplete()
                    })
                }
                Method.UPLOAD -> {
                    if (fileKey != null && filePath != null) {
                        net?.upload(url, fileKey, filePath, fileName, fileType, formData, paths, params, headers, listener,callBack = {
                            if (!emitter.isDisposed)
                                emitter.onNext(it)
                            net?.close()
                            net = null
                            emitter.onComplete()
                        })
                    }
                }
                else -> {
                    net?.get(url, paths, params, headers, callBack = {
                        if (!emitter.isDisposed)
                            emitter.onNext(it)
                        emitter.onComplete()
                    })
                }
            }
        }
        observable
            .subscribeOn(runOn ?: Schedulers.io())
            .observeOn(receiverOn ?: Schedulers.newThread())
            .subscribe(object : Observer<NetResponse<String>> {
                var di: Disposable? = null;
                override fun onComplete() {
                    di?.dispose()
                }

                override fun onError(e: Throwable) {
                    di?.dispose()
                }

                override fun onNext(t: NetResponse<String>) {
                    sub.onNext(t)
                }

                override fun onSubscribe(d: Disposable) {
                    di = d
                }
            })
        return sub
    }

    fun getSync(
        url: String?,
        method: Method = Method.GET,
        paths: String? = null,
        body: Any? = null,
        params: Map<String, String?>? = null,
        headers: Headers? = null,
        client: OkHttpClient? = null
    ): Subject<NetResponse<String>> = callSync(url, method, paths, body, params, headers, client)

    fun postSync(
        url: String?,
        method: Method = Method.POST,
        paths: String? = null,
        body: Any? = null,
        params: Map<String, String?>? = null,
        headers: Headers? = null,
        client: OkHttpClient? = null
    ): Subject<NetResponse<String>> = callSync(url, method, paths, body, params, headers, client)

    fun deleteSync(
        url: String?,
        method: Method = Method.DELETE,
        paths: String? = null,
        body: Any? = null,
        params: Map<String, String?>? = null,
        headers: Headers? = null,
        client: OkHttpClient? = null
    ): Subject<NetResponse<String>> = callSync(url, method, paths, body, params, headers, client)

    fun putSync(
        url: String?,
        method: Method = Method.PUT,
        paths: String? = null,
        body: Any? = null,
        params: Map<String, String?>? = null,
        headers: Headers? = null,
        client: OkHttpClient? = null
    ): Subject<NetResponse<String>> = callSync(url, method, paths, body, params, headers, client)

    fun headSync(
        url: String?,
        method: Method = Method.HEAD,
        paths: String? = null,
        body: Any? = null,
        params: Map<String, String?>? = null,
        headers: Headers? = null,
        client: OkHttpClient? = null
    ): Subject<NetResponse<String>> = callSync(url, method, paths, body, params, headers, client)

    fun patchSync(
        url: String?,
        method: Method = Method.PATCH,
        paths: String? = null,
        body: Any? = null,
        params: Map<String, String?>? = null,
        headers: Headers? = null,
        client: OkHttpClient? = null
    ): Subject<NetResponse<String>> = callSync(url, method, paths, body, params, headers, client)

    fun uploadSync(
        url: String?,
        fileKey: String,
        filePath: String,
        fileName: String? = null,
        fileType: String? = null,
        formData: Map<String, String>? = null,
        listener: ((bytesWritten: Long, contentLength: Long) -> Unit)? = null,
        paths: String? = null,
        params: Map<String, String?>? = null,
        headers: Headers? = null,
        client: OkHttpClient? = null
    ): Subject<NetResponse<String>> {
        return callSync(
            url,
            method = Method.UPLOAD,
            paths = paths,
            body = null,
            params = params,
            headers = headers,
            client = client,
            fileKey = fileKey,
            filePath = filePath,
            fileName = fileName,
            fileType = fileType,
            formData = formData,
            listener = listener
        )
    }

    fun callSync(
        url: String?,
        method: Method = Method.GET,
        paths: String? = null,
        body: Any? = null,
        params: Map<String, String?>? = null,
        headers: Headers? = null,
        client: OkHttpClient? = null,
        fileKey: String? = null,
        filePath: String? = null,
        fileName: String? = null,
        fileType: String? = null,
        formData: Map<String, String>? = null,
        listener: ((bytesWritten: Long, contentLength: Long) -> Unit)? = null
    ): Subject<NetResponse<String>> {
        val sub: Subject<NetResponse<String>> = BehaviorSubject.create();
        var net: NetClient? = NetClient(client ?: Network.client.build(), applicationContext)
        val observable: Observable<NetResponse<String>> = Observable.create<NetResponse<String>> { emitter ->
            val json: String? = when (body) {
                is String? -> body
                else -> modelTo(body)
            }
            when (method) {
                Method.HEAD -> {
                    net?.head(url, paths, params, headers, callBack = {
                        if (!emitter.isDisposed)
                            emitter.onNext(it)
                        net?.close()
                        net = null
                        emitter.onComplete()
                    })
                }
                Method.PATCH -> {
                    net?.patch(url, paths, json, params, headers, callBack = {
                        if (!emitter.isDisposed)
                            emitter.onNext(it)
                        net?.close()
                        net = null
                        emitter.onComplete()
                    })
                }
                Method.DELETE -> {
                    net?.delete(url, paths, json, params, headers, callBack = {
                        if (!emitter.isDisposed)
                            emitter.onNext(it)
                        net?.close()
                        net = null
                        emitter.onComplete()
                    })
                }
                Method.POST -> {
                    net?.post(url, paths, json, params, headers, callBack = {
                        if (!emitter.isDisposed)
                            emitter.onNext(it)
                        net?.close()
                        net = null
                        emitter.onComplete()
                    })
                }
                Method.PUT -> {
                    net?.put(url, paths, json, params, headers, callBack = {
                        if (!emitter.isDisposed)
                            emitter.onNext(it)
                        net?.close()
                        net = null
                        emitter.onComplete()
                    })
                }
                Method.UPLOAD -> {
                    if (fileKey != null && filePath != null) {
                        net?.upload(url, fileKey, filePath, fileName, fileType, formData, paths, params, headers, listener, callBack = {
                            if (!emitter.isDisposed)
                                emitter.onNext(it)
                            net?.close()
                            net = null
                            emitter.onComplete()
                        })
                    }
                }
                else -> {
                    net?.get(url, paths, params, headers, callBack = {
                        if (!emitter.isDisposed)
                            emitter.onNext(it)
                        net?.close()
                        net = null
                        emitter.onComplete()
                    })
                }
            }
        }
        observable
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(object : Observer<NetResponse<String>> {
                var di: Disposable? = null;
                override fun onComplete() {
                    di?.dispose()
                }

                override fun onError(e: Throwable) {
                    di?.dispose()
                }

                override fun onNext(t: NetResponse<String>) {
                    sub.onNext(t)
                }

                override fun onSubscribe(d: Disposable) {
                    di = d
                }
            })
        return sub
    }

    fun callWithoutRx(
        url: String?,
        method: Method = Method.GET,
        callBack: ((net: NetResponse<String>) -> Unit)? = null,
        paths: String? = null,
        body: Any? = null,
        params: Map<String, String?>? = null,
        headers: Headers? = null,
        client: OkHttpClient? = null,
        fileKey: String? = null,
        filePath: String? = null,
        fileName: String? = null,
        fileType: String? = null,
        formData: Map<String, String>? = null,
        listener: ((bytesWritten: Long, contentLength: Long) -> Unit)? = null
    ): Call? {
        val net: NetClient? = NetClient(client ?: Network.client.build(), applicationContext)
        val json: String? = when (body) {
            is String? -> body
            else -> modelTo(body)
        }
        when (method) {
            Method.HEAD -> {
                return net?.head(url, paths, params, headers, callBack = {
                    callBack?.invoke(it)
                })
            }
            Method.PATCH -> {
                return net?.patch(url, paths, json, params, headers, callBack = {
                    callBack?.invoke(it)
                })
            }
            Method.DELETE -> {
                return net?.delete(url, paths, json, params, headers, callBack = {
                    callBack?.invoke(it)
                })
            }
            Method.POST -> {
                return net?.post(url, paths, json, params, headers, callBack = {
                    callBack?.invoke(it)
                })
            }
            Method.PUT -> {
                return net?.put(url, paths, json, params, headers, callBack = {
                    callBack?.invoke(it)
                })
            }
            Method.UPLOAD -> {
                if (fileKey != null && filePath != null) {
                    return net?.upload(url, fileKey, filePath, fileName, fileType, formData, paths, params, headers, listener, callBack = {
                        callBack?.invoke(it)
                    })
                }
            }
            else -> {
                return net?.get(url, paths, params, headers, callBack = {
                    callBack?.invoke(it)
                })
            }
        }
        return null
    }
}