package com.app.core.ui.view

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.annotation.ColorInt
import android.support.annotation.MainThread
import android.text.style.ReplacementSpan

@MainThread
internal class UIRoundedBackgroundSpan(@ColorInt rBackground: Int, @ColorInt rTextColor: Int,
                                       radius: Float = 0f
) : ReplacementSpan() {
    private var backgroundColor = 0
    private var textColor = 0
    private var cornerRadius: Float = 0f

    init {
        backgroundColor = rBackground
        textColor = rTextColor
        cornerRadius = radius
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val rect = RectF(x, top.toFloat(), x + measureText(paint, text, start, end), bottom.toFloat())
        paint.color = backgroundColor
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint)
        paint.color = textColor
        canvas.drawText(text, start, end, x, y.toFloat(), paint)
    }

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        return Math.round(paint.measureText(text, start, end))
    }

    private fun measureText(paint: Paint, text: CharSequence, start: Int, end: Int): Float {
        return paint.measureText(text, start, end)
    }
}
