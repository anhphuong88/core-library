package com.app.core.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.ViewTreeObserver
import com.app.core.util.StringUtil

class AntiWordBreakTextView : AppCompatTextView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, text: String) : super(context) {
        setTextSource(text)
    }

    fun setTextSource(text: String?) {
        if (text.isNullOrBlank()) {
            setText(text)
        } else {
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                @SuppressLint("ObsoleteSdkInt")
                @Suppress("DEPRECATION")
                override fun onGlobalLayout() {
                    val textViewWidth = width.toFloat()

                    val sentences = text.split("\\s".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    var measureText = StringBuilder()
                    val textPaint = paint
                    if (sentences.size >= 2) {
                        for (i in sentences.indices) {
                            val textRect = Rect()
                            textPaint.getTextBounds(getText().toString(), 0, getText().toString().length, textRect)
                            val textWidth = (textRect.left + textRect.width()).toFloat()
                            if (textWidth < textViewWidth) {
                                measureText.append(sentences[i]).append(" ")
                                setText(measureText)
                            } else if (i >= 1) {
                                measureText = StringBuilder(sentences[i - 1] + " " + sentences[i])
                                sentences[i - 1] = "\n" + sentences[i - 1]
                                setText(measureText)
                            }
                        }
                    }
                    measureText = StringBuilder()
                    for (sentence in sentences) {
                        measureText.append(sentence).append(" ")
                    }
                    setText(measureText.toString())
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        viewTreeObserver.removeOnGlobalLayoutListener(this)
                    } else {
                        viewTreeObserver.removeGlobalOnLayoutListener(this)
                    }
                }
            })
        }
    }
}
