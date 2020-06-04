package com.app.core.ui.image

import android.content.Context
import com.app.core.network.Network
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.common.util.ByteConstants
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import com.facebook.imagepipeline.cache.MemoryCacheParams
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig

object Config {
    private val MAX_MEMORY_CACHE_SIZE = 40 * ByteConstants.MB
    private val MAX_SMALL_DISK_VERYLOW_CACHE_SIZE = 5 * ByteConstants.MB
    private val MAX_SMALL_DISK_LOW_CACHE_SIZE = 10 * ByteConstants.MB
    private val MAX_SMALL_DISK_CACHE_SIZE = 20 * ByteConstants.MB
    private val MAX_DISK_CACHE_VERYLOW_SIZE = 10 * ByteConstants.MB
    private val MAX_DISK_CACHE_LOW_SIZE = 20 * ByteConstants.MB
    private val MAX_DISK_CACHE_SIZE = 100 * ByteConstants.MB
    private val cacheFolder = "image"

    fun init(context: Context?) {
        val builder = OkHttpImagePipelineConfigFactory.newBuilder(context, Network.createClient(retry = 10))
        val memoryCacheParams = MemoryCacheParams(
                MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
                Integer.MAX_VALUE, // Max entries in the cache
                MAX_MEMORY_CACHE_SIZE, // Max total size of elements in eviction queue
                Integer.MAX_VALUE, // Max length of eviction queue
                Integer.MAX_VALUE) // Max cache entry side
        val diskSmallCacheConfig = DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryPath(context?.externalCacheDir)
                .setBaseDirectoryName(cacheFolder)
                .setMaxCacheSize(MAX_SMALL_DISK_CACHE_SIZE.toLong())
                .setMaxCacheSizeOnLowDiskSpace(MAX_SMALL_DISK_LOW_CACHE_SIZE.toLong())
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_SMALL_DISK_VERYLOW_CACHE_SIZE.toLong())
                .build()
        val diskCacheConfig = DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryPath(context?.externalCacheDir)
                .setBaseDirectoryName(cacheFolder)
                .setMaxCacheSize(MAX_DISK_CACHE_SIZE.toLong())
                .setMaxCacheSizeOnLowDiskSpace(MAX_DISK_CACHE_LOW_SIZE.toLong())
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_DISK_CACHE_VERYLOW_SIZE.toLong())
                .build()
        builder.setBitmapMemoryCacheParamsSupplier { memoryCacheParams }
                .setMainDiskCacheConfig(diskCacheConfig)
                .setSmallImageDiskCacheConfig(diskSmallCacheConfig)
        builder.isDownsampleEnabled = true
        val jpegConfig = SimpleProgressiveJpegConfig()
        jpegConfig.getNextScanNumberToDecode(0)
        jpegConfig.getQualityInfo(0)
        builder.setProgressiveJpegConfig(jpegConfig)
        val config = builder.build()
        Fresco.initialize(context, config)
    }

    fun clearCache() {
        val imagePipeline = Fresco.getImagePipeline()
        imagePipeline.clearMemoryCaches()
    }
}
