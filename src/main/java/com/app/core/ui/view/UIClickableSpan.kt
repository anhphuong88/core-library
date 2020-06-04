package com.app.core.ui.view

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

internal class UIClickableSpan(private val action: () -> Unit) : ClickableSpan() {

    override fun onClick(widget: View) {
        action()
    }

    override fun updateDrawState(drawState: TextPaint) {
        super.updateDrawState(drawState)
        drawState.isUnderlineText = false
    }
}