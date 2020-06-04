package com.app.core.ui.view

import android.graphics.*
import kotlin.math.absoluteValue
import android.support.v4.view.ViewCompat.setAlpha

fun Bitmap.cropFace(rect: Rect): Bitmap {
    val w = rect.right - rect.left
    val h = rect.bottom - rect.top
    val ret = Bitmap.createBitmap(w, h, config)
    val left = -rect.left.toFloat()
    val top = -rect.top.toFloat()
    val canvas = Canvas(ret)
    canvas.drawBitmap(this, left, top, null);
    return ret
}

fun Bitmap.cropFaceBigger(rect: Rect): Bitmap {
    var w = (rect.right - rect.left).absoluteValue
    val rateLR = (0.5f * w).toInt()

    w += if (rect.right + rateLR > width) {
        width - rect.right
    } else {
        rateLR
    }

    w += if (rect.left - rateLR < 0) {
        rect.left
    } else {
        rateLR
    }

    var h = ((rect.bottom - rect.top) * 2f).toInt()

    var rateTop = (h - (rect.bottom - rect.top))/ 2

    val hTop = if (rect.top - rateTop < 0) {
        0
    } else {
        rect.top - rateTop
    }

    val wLeft = if (rect.left - rateLR < 0) {
        0
    } else {
        rect.left - rateLR
    }

    val ret = Bitmap.createBitmap(w, h, config)
    val canvas = Canvas(ret)
    canvas.drawBitmap(this, -wLeft.toFloat(), -hTop.toFloat(), null);
    return ret
}

fun Bitmap.cropFaceHeadPose(rect: Rect): Bitmap {
    var w = (rect.right - rect.left).absoluteValue
    val rateLR = (0.25f * w).toInt()

    w += if (rect.right + rateLR > width) {
        width - rect.right
    } else {
        rateLR
    }

    w += if (rect.left - rateLR < 0) {
        rect.left
    } else {
        rateLR
    }

    val h = if (rect.bottom - rect.top > w) rect.bottom - rect.top else if (w < height) w else height

    val rateTop = (h - (rect.bottom - rect.top))/ 2

    val hTop = if (rect.top - rateTop < 0) {
        0
    } else {
        rect.top - rateTop
    }

    val wLeft = if (rect.left - rateLR < 0) {
        0
    } else {
        rect.left - rateLR
    }

    val ret = Bitmap.createBitmap(w, h, config)
    val canvas = Canvas(ret)
    canvas.drawBitmap(this, -wLeft.toFloat(), -hTop.toFloat(), null);
    return ret
}

/**
 * Crop face, without ears.
 */
fun Bitmap.cropFaceSmaller(rect: Rect): Bitmap {
    val leftOffset = 0.1f * (rect.right - rect.left)
    val w = (rect.right - rect.left) * 0.8

    val h = rect.bottom - rect.top
    val ret = Bitmap.createBitmap(w.toInt(), h, config)
    val left = -rect.left.toFloat() + leftOffset
    val top = -rect.top.toFloat()
    val canvas = Canvas(ret)
    canvas.drawBitmap(this, left, top, null)
    return ret
}


fun Bitmap.crop2Face(): Pair<Bitmap, Bitmap> {
    val middlePoint = (width / 2).toFloat()
    val rightFace = Bitmap.createBitmap(middlePoint.toInt(), height, config)
    val rightFaceCanvas = Canvas(rightFace)

    rightFaceCanvas.drawBitmap(this, 0f, 0f, null)

    val leftFace = Bitmap.createBitmap(middlePoint.toInt(), height, config)
    val leftFaceCanvas = Canvas(leftFace)

    leftFaceCanvas.drawBitmap(this, -middlePoint, 0f, null)
    return Pair(rightFace, leftFace)
}

fun Bitmap.cropFaceFull(rect: Rect): Bitmap {
//        Logger.w("bitmap ${bitmap.width} ${bitmap.height}")
    var w = (rect.right - rect.left).absoluteValue
//    var h = (rect.bottom - rect.top).absoluteValue

    val rate = (0.5 * w).toInt()
    val rate2 = (0.5 * w).toInt()
    val rateh = (2.5 * w).toInt()

    w += if (rect.right + rate > width) {
        width - rect.right
    } else {
        rate
    }

    w += if (rect.left - rate < 0) {
        rect.left
    } else {
        rate
    }

    val hTop = if (rect.top - rate2 < 0) {
        0
    } else {
        rect.top - rate2
    }

    val wLeft = if (rect.left - rate < 0) {
        0
    } else {
        rect.left - rate
    }

    val h = if (hTop + rateh > height) {
        height - hTop
    } else {
        rateh
    }

    val ret = Bitmap.createBitmap(w, h, config)
    val canvas = Canvas(ret)
    canvas.drawBitmap(this, -wLeft.toFloat(), -hTop.toFloat(), null)

    return ret
}

fun Bitmap.cropFaceLandscape(rect: Rect): Bitmap {
    var w = (rect.right - rect.left).absoluteValue

    val rateLR = (0.2 * w).toInt()
    val rateTop = (0.5 * w).toInt()
    val rateHeight = (2 * w).toInt()

    w += if (rect.right + rateLR > width) {
        width - rect.right
    } else {
        rateLR
    }

    w += if (rect.left - rateLR < 0) {
        rect.left
    } else {
        rateLR
    }

    val hTop = if (rect.top - rateTop < 0) {
        0
    } else {
        rect.top - rateTop
    }

    val wLeft = if (rect.left - rateLR < 0) {
        0
    } else {
        rect.left - rateLR
    }

    val h = if (hTop + rateHeight > height) {
        height - hTop
    } else {
        rateHeight
    }

    val ret = Bitmap.createBitmap(w, h, config)
    val canvas = Canvas(ret)
    canvas.drawBitmap(this, -wLeft.toFloat(), -hTop.toFloat(), null);
    return ret
}

fun Bitmap.cropFaceCover(rect: Rect): Bitmap {
    val w = (rect.right - rect.left)
    val h = (rect.bottom - rect.top) * 4 / 3    //Cover img ratio.
    val marginTop = 0.2 * w

    val ret = Bitmap.createBitmap(w, h, config)
    val left = -rect.left.toFloat()
    val top = -rect.top + marginTop.toFloat()
    val canvas = Canvas(ret)
    canvas.drawBitmap(this, left, top, null)
    return ret
}


fun Bitmap.rotateBitmap(angle: Float): Bitmap {
    val matrix = Matrix()
    matrix.preRotate(angle)
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun Bitmap.cropBitmapTransparency(): Bitmap? {
    var minX = width
    var minY = height
    var maxX = -1
    var maxY = -1
    for (y in 0 until height) {
        for (x in 0 until width) {
            val alpha = getPixel(x, y) shr 24 and 255
            if (alpha > 0)
            // pixel is not 100% transparent
            {
                if (x < minX)
                    minX = x
                if (x > maxX)
                    maxX = x
                if (y < minY)
                    minY = y
                if (y > maxY)
                    maxY = y
            }
        }
    }
    return if (maxX < minX || maxY < minY) null else Bitmap.createBitmap(this, minX, minY, maxX - minX + 1, maxY - minY + 1) // Bitmap is entirely transparent
}