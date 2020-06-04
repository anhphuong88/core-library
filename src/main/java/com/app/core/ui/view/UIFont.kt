package com.app.core.ui.view

import android.content.res.AssetManager
import android.graphics.Typeface
import java.lang.Exception

internal object UIFont {
    private var sCachedFonts: MutableMap<String, Typeface?> = HashMap()

    @Synchronized fun load(assetManager: AssetManager, path: String): Typeface? {
        return synchronized(sCachedFonts) {
            try {
                if (!sCachedFonts.containsKey(path)) {
                    val typeface: Typeface = Typeface.createFromAsset(assetManager, path)
                    sCachedFonts[path] = typeface
                }
            } catch (e: Exception) {
                sCachedFonts[path] = null
            }
            sCachedFonts[path]
        }
    }
}