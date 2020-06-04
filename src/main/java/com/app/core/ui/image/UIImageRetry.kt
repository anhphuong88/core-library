package com.app.core.ui.image

import android.net.Uri
import android.support.annotation.Px
import com.app.core.ui.view.width
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.request.ImageRequestBuilder

class UIImageRetry {
    private var photosSuccess: Int = 0

    private fun SimpleDraweeView.resizeImageDownload(
        startPos: Int = 0,
        listener: BaseControllerListener<ImageInfo>,
        urls: List<String>?,
        @Px w: Int = 0,
        @Px h: Int = 0
    ) {
        if (startPos < (urls?.size ?: 0) && startPos < 4 && urls!![startPos].isNotEmpty()) {
            val uri = Uri.parse(urls[startPos])
            if (uri != null) {
                val builder = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setProgressiveRenderingEnabled(true)
                    //.setRequestPriority(Priority.MEDIUM)
                    //.setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                    .setLocalThumbnailPreviewsEnabled(true)
                if (h > 0) {
                    val width = if (w > 0) w else context.width()
                    builder.resizeOptions = ResizeOptions(width, h)
                }
                val request = builder.build()
                controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(controller)
                    .setControllerListener(listener)
                    .setAutoPlayAnimations(true)
                    .setImageRequest(request)
                    .build()
            }
        }
    }

    private fun SimpleDraweeView.resizeImageDownload(
        urls: List<String>?,
        @Px w: Int = 0,
        @Px h: Int = 0
    ) {
        if (urls?.size ?: 0 > 0 && photosSuccess < urls?.size ?: 0 && photosSuccess < 4) {
            val listener = object : BaseControllerListener<ImageInfo>() {
                private var retry = 0
                private var maxRetry = 10

                override fun onFailure(id: String?, throwable: Throwable) {
                    var isContinue = false
//                    Logger.w("onFailure: " + urls.size + ", " + urls!![photosSuccess] + " " + throwable.message)
                    if (retry < maxRetry && (!(throwable.message.isNullOrBlank()) && (throwable.message!!.contains("decod") || throwable.message!!.contains(
                            "timeout"
                        ) || throwable.message!!.contains("corrupt")) || throwable.message == null)
                    ) {
                        isContinue = true
                        if (!(throwable.message.isNullOrBlank()) && throwable.message!!.contains("decod")) {
                            maxRetry = 20
                        }
                        retry++
                    }
                    if (!isContinue && photosSuccess < (urls?.size ?: 0) - 1 && photosSuccess < 3) {
                        photosSuccess++
                        isContinue = true
                    }
                    if (isContinue)
                        resizeImageDownload(photosSuccess, this, urls, w, h)
                }
            }
            resizeImageDownload(photosSuccess, listener, urls, w, h)
        }
    }

    fun download(image: SimpleDraweeView?, urls: List<String>?, @Px w: Int = 0, @Px h: Int = 0) {
        image?.resizeImageDownload(urls, w, h)
    }
}

fun SimpleDraweeView.download(urls: List<String>?, @Px w: Int = 0, @Px h: Int = 0) {
    UIImageRetry().download(this, urls, w, h)
}

fun SimpleDraweeView.download(url: String?, @Px w: Int = 0, @Px h: Int = 0) {
    url?.let {
        UIImageRetry().download(this, listOf(it), w, h)
    }
}

