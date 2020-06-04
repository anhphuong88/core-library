package com.app.core.ui.view

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.TypedValue
import android.view.Gravity
import android.widget.EditText
import android.widget.TextView
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import com.app.core.action.Action
import com.app.core.util.range
import com.app.core.util.rangeLast
import java.util.*


fun TextView.font(path: String?) {
    if (path?.isNotBlank() == true)
        typeface = context.font(path)
}

fun TextView.styleSingle(@Px size: Int, @ColorRes rColor: Int, fontPath: String? = "") {
    tSize(size)
    setTextColor(context.color(rColor))
    maxLines = 1
    setLines(1)
    isHorizontalScrollBarEnabled = false
    isHorizontalFadingEdgeEnabled = false
    setHorizontallyScrolling(false)

    font(fontPath)
    ellipsize = TextUtils.TruncateAt.END
}


fun TextView.styleSingle(@Px size: Int, color: String? = "", fontPath: String? = "") {
    tSize(size)
    setTextColor(context.color(color) ?: android.R.color.transparent)
    maxLines = 1
    setLines(1)
    isHorizontalScrollBarEnabled = false
    isHorizontalFadingEdgeEnabled = false
    setHorizontallyScrolling(false)

    font(fontPath)
    ellipsize = TextUtils.TruncateAt.END
}


fun TextView.styleMultiple(
    @Px size: Int, @ColorRes rColor: Int, fontPath: String? = "",
    startLine: Int = 1,
    maxLine: Int = Int.MAX_VALUE
) {
    tSize(size)
    setTextColor(context.color(rColor))
    if (startLine > 1) setLines(startLine)
    maxLines = maxLine
    font(fontPath)
    ellipsize = TextUtils.TruncateAt.END
}

fun TextView.styleMultiple(
    @Px size: Int, color: String? = "", fontPath: String? = "",
    startLine: Int = 1,
    maxLine: Int = Int.MAX_VALUE
) {
    tSize(size)
    setTextColor(context.color(color) ?: android.R.color.transparent)
    if (startLine > 1) setLines(startLine)
    maxLines = maxLine
    font(fontPath)
    ellipsize = TextUtils.TruncateAt.END
}

fun TextView.underLine() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun TextView.bold() {
    setTypeface(typeface, Typeface.BOLD)
}

fun TextView.italic() {
    setTypeface(typeface, Typeface.ITALIC)
}

fun TextView.boldItalic() {
    setTypeface(typeface, Typeface.BOLD_ITALIC)
}

fun TextView.normal() {
    setTypeface(typeface, Typeface.NORMAL)
}

/**
 * @param value need to bring the original float value to multiply with 0.025
 */
fun TextView.spacing(value: Float) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        letterSpacing = value * 0.025f
    }
}

fun TextView.tSize(value: Int) {
    setTextSize(TypedValue.COMPLEX_UNIT_PX, value.toFloat())
}

fun TextView.tText(): String {
    return if (text != null) text.toString() else ""
}

fun TextView.tText(value: String?) {
    text = value
}

fun TextView.tText(@StringRes rsText: Int) {
    text = context.text(rsText)
}

fun TextView.tHint(): String {
    return if (hint != null) hint.toString() else ""
}

fun TextView.tHint(value: String?) {
    hint = value
}

fun TextView.tHint(@StringRes rsText: Int) {
    hint = context.text(rsText)
}

fun TextView.color(@ColorRes rColor: Int) {
    setTextColor(context.color(rColor))
}

fun TextView.color(color: String?) {
    val colorInt = context.color(color)
    colorInt?.let {
        setTextColor(colorInt)
    }
}


fun TextView.color(@ColorRes rColor: Int, @ColorRes rSelectedColor: Int) {

    val colorStateList = ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_pressed),
            intArrayOf(-android.R.attr.state_pressed)
        ),
        intArrayOf(
            colorInt(rColor),
            colorInt(rSelectedColor)
        )
    )
    setTextColor(colorStateList)
}

fun TextView.color(color: String?, selectedColor: String?) {
    val colorInt = context.color(color)
    val colorSelectedInt = context.color(selectedColor)
    if (colorInt != null && colorSelectedInt != null ) {
        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_pressed),
                intArrayOf(android.R.attr.state_pressed)
            ),
            intArrayOf(
                colorInt,
                colorSelectedInt
            )
        )
        setTextColor(colorStateList)
    }
}

fun TextView.hintColor(@ColorRes rColor: Int) {
    setHintTextColor(context.color(rColor))
}

/* Advanced Text */
fun TextView.highLightFirst(
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, @ColorRes rColor: Int = android.R.color.transparent
) {
    val range = tText().toLowerCase(Locale.getDefault()).range(input?.toLowerCase(Locale.getDefault()))
    highLight(range[0], range[1], fontPath, size, rColor)
}

fun TextView.highLightFirst(
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, @SuppressLint("ResourceType") @ColorRes rTextColor: Int = currentTextColor, @ColorRes rBGColor: Int = android.R.color.transparent
) {
    val range = tText().range(input?.toLowerCase(Locale.getDefault()))
    highLight(range[0], range[1], fontPath, size, rTextColor, rBGColor)
}

fun TextView.highLightFirstAndClick(
    action: () -> Unit,
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, @ColorRes rColor: Int = android.R.color.transparent
) {
    val range = tText().toLowerCase(Locale.getDefault()).range(input?.toLowerCase(Locale.getDefault()))
    highLightAndClick(action, range[0], range[1], fontPath, size, rColor)
}

fun TextView.highLightFirstAndClick(
    action: () -> Unit,
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, @SuppressLint("ResourceType") @ColorRes rTextColor: Int = currentTextColor, @ColorRes rBGColor: Int = android.R.color.transparent
) {
    val range = tText().toLowerCase(Locale.getDefault()).range(input?.toLowerCase(Locale.getDefault()))
    highLightAndClick(action, range[0], range[1], fontPath, size, rTextColor, rBGColor)
}

fun TextView.highLightLast(
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, @ColorRes rColor: Int = android.R.color.transparent
) {
    val range = tText().toLowerCase(Locale.getDefault()).rangeLast(input?.toLowerCase(Locale.getDefault()))
    highLight(range[0], range[1], fontPath, size, rColor)
}

fun TextView.highLightLast(
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, @SuppressLint("ResourceType") @ColorRes rTextColor: Int = currentTextColor, @ColorRes rBGColor: Int = android.R.color.transparent
) {
    val range = tText().rangeLast(input?.toLowerCase(Locale.getDefault()))
    highLight(range[0], range[1], fontPath, size, rTextColor, rBGColor)
}

fun TextView.highLight(
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, @ColorRes rColor: Int = android.R.color.transparent
) {
    var range = tText().toLowerCase(Locale.getDefault()).range(input?.toLowerCase(Locale.getDefault()))
    if (range[0] >= 0) {
        val typeFont = context.font(fontPath)
        val startSize = textSize
        val span = SpannableString(tText())
        while (range[0] >= 0 && range[1] <= tText().length) {
            span.setSpan(RelativeSizeSpan(size / startSize), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            typeFont?.apply {
                span.setSpan(UITypefaceSpan(this), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            }
            span.setSpan(
                ForegroundColorSpan(context.color(rColor)),
                range[0],
                range[1],
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            range = tText().toLowerCase(Locale.getDefault()).range(input?.toLowerCase(Locale.getDefault()), range[1])
        }
        setText(span, TextView.BufferType.NORMAL)
    }
}

fun TextView.highLight(
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, @SuppressLint("ResourceType") @ColorRes rTextColor: Int = currentTextColor, @ColorRes rBGColor: Int = android.R.color.transparent
) {
    var range = tText().toLowerCase(Locale.getDefault()).range(input?.toLowerCase(Locale.getDefault()))
    if (range[0] >= 0) {
        val typeFont = context.font(fontPath)
        val startSize = textSize
        val span = SpannableString(tText())
        while (range[0] >= 0 && range[1] <= tText().length) {
            span.setSpan(RelativeSizeSpan(size / startSize), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            typeFont?.apply {
                span.setSpan(UITypefaceSpan(this), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            }
            span.setSpan(
                UIRoundedBackgroundSpan(context.color(rBGColor), context.color(rTextColor)),
                range[0],
                range[1],
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            range = tText().toLowerCase(Locale.getDefault()).range(input?.toLowerCase(Locale.getDefault()), range[1])
        }
        setText(span, TextView.BufferType.NORMAL)
    }
}

fun TextView.highLightMultiple(
    inputList: List<String?>,
    fontPath: String? = "",
    size: Float = textSize, @ColorRes rColor: Int = android.R.color.transparent
) {
    val span = SpannableString(tText())
    for (input in inputList) {
        val textStr = tText().toLowerCase(Locale.getDefault())
        val inStr = input?.toLowerCase(Locale.getDefault())
        val strings = inStr?.trim()?.split("\\s+")
        val typeFont = context.font(fontPath)
        val startSize = textSize
        var range: IntArray
        strings?.apply {
            for (str in this) {
                range = textStr.range(str)
                while (range[0] >= 0 && range[1] <= tText().length) {
                    span.setSpan(
                        RelativeSizeSpan(size / startSize),
                        range[0],
                        range[1],
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    typeFont?.apply {
                        span.setSpan(
                            UITypefaceSpan(this),
                            range[0],
                            range[1],
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE
                        )
                    }
                    span.setSpan(
                        ForegroundColorSpan(context.color(rColor)),
                        range[0],
                        range[1],
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    range = textStr.range(inStr, range[1])
                }
            }
        }
    }
    setText(span, TextView.BufferType.SPANNABLE)
}


fun TextView.highLightMultiple(
    inputList: List<String?>,
    fontPath: String? = "",
    size: Float = textSize, @SuppressLint("ResourceType") @ColorRes rTextColor: Int = currentTextColor, @ColorRes rBGColor: Int = android.R.color.transparent
) {
    val span = SpannableString(tText())
    for (input in inputList) {
        val textStr = tText().toLowerCase(Locale.getDefault())
        val inStr = input?.toLowerCase(Locale.getDefault())
        val strings = inStr?.trim()?.split("\\s+")
        val typeFont = context.font(fontPath)
        val startSize = textSize
        var range: IntArray
        strings?.apply {
            for (str in this) {
                range = textStr.range(str)
                while (range[0] >= 0 && range[1] <= tText().length) {
                    span.setSpan(
                        RelativeSizeSpan(size / startSize),
                        range[0],
                        range[1],
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    typeFont?.apply {
                        span.setSpan(
                            UITypefaceSpan(this),
                            range[0],
                            range[1],
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE
                        )
                    }
                    span.setSpan(
                        UIRoundedBackgroundSpan(context.color(rBGColor), context.color(rTextColor)),
                        range[0],
                        range[1],
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    range = textStr.range(inStr, range[1])
                }
            }
        }
    }
    setText(span, TextView.BufferType.SPANNABLE)
}

fun TextView.highLightMultipleAndClick(
    inputList: List<String?>,
    inputListAction: List<(() -> Unit)?>,
    fontPath: String? = "",
    size: Float = textSize, @ColorRes rColor: Int = android.R.color.transparent
) {
    val span = SpannableString(tText())
    var i = 0
    for (input in inputList) {
        val textStr = tText().toLowerCase(Locale.getDefault())
        val inStr = input?.toLowerCase(Locale.getDefault())
        val strings = inStr?.trim()?.split("\\s+")
        val typeFont = context.font(fontPath)
        val startSize = textSize
        var range: IntArray
        strings?.apply {
            for (str in this) {
                range = textStr.range(str)
                while (range[0] >= 0 && range[1] <= tText().length) {
                    span.setSpan(
                        RelativeSizeSpan(size / startSize),
                        range[0],
                        range[1],
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    typeFont?.apply {
                        span.setSpan(
                            UITypefaceSpan(this),
                            range[0],
                            range[1],
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE
                        )
                    }
                    if (inputListAction.size >= i && inputListAction[i] != null) {
                        inputListAction[i]?.let { action ->
                            span.setSpan(
                                UIClickableSpan(action),
                                range[0],
                                range[1],
                                Spannable.SPAN_INCLUSIVE_INCLUSIVE
                            )
                        }
                    }
                    span.setSpan(
                        ForegroundColorSpan(context.color(rColor)),
                        range[0],
                        range[1],
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    range = textStr.range(inStr, range[1])
                }
            }
        }
        i++;
    }
    movementMethod = LinkMovementMethod.getInstance()
    setText(span, TextView.BufferType.SPANNABLE)
}


fun TextView.highLightMultipleAndClick(
    inputList: List<String?>,
    inputListAction: List<(() -> Unit)?>,
    fontPath: String? = "",
    size: Float = textSize, @SuppressLint("ResourceType") @ColorRes rTextColor: Int = currentTextColor, @ColorRes rBGColor: Int = android.R.color.transparent
) {
    val span = SpannableString(tText())
    var i = 0
    for (input in inputList) {
        val textStr = tText().toLowerCase(Locale.getDefault())
        val inStr = input?.toLowerCase(Locale.getDefault())
        val strings = inStr?.trim()?.split("\\s+")
        val typeFont = context.font(fontPath)
        val startSize = textSize
        var range: IntArray
        strings?.apply {
            for (str in this) {
                range = textStr.range(str)
                while (range[0] >= 0 && range[1] <= tText().length) {
                    span.setSpan(
                        RelativeSizeSpan(size / startSize),
                        range[0],
                        range[1],
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    typeFont?.apply {
                        span.setSpan(
                            UITypefaceSpan(this),
                            range[0],
                            range[1],
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE
                        )
                    }
                    if (inputListAction.size >= i && inputListAction[i] != null) {
                        inputListAction[i]?.let { action ->
                            span.setSpan(
                                UIClickableSpan(action),
                                range[0],
                                range[1],
                                Spannable.SPAN_INCLUSIVE_INCLUSIVE
                            )
                        }
                    }
                    span.setSpan(
                        UIRoundedBackgroundSpan(context.color(rBGColor), context.color(rTextColor)),
                        range[0],
                        range[1],
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    range = textStr.range(inStr, range[1])
                }
            }
        }
        i++;
    }
    movementMethod = LinkMovementMethod.getInstance()
    setText(span, TextView.BufferType.SPANNABLE)
}

fun TextView.highLightSentence(
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, @ColorRes rColor: Int = android.R.color.transparent
) {
    val textStr = tText().toLowerCase(Locale.getDefault())
    val inStr = input?.toLowerCase(Locale.getDefault())
    val strings = inStr?.trim()?.split("\\s+")
    val typeFont = context.font(fontPath)
    val startSize = textSize
    val span = SpannableString(tText())
    var range: IntArray
    strings?.apply {
        for (str in this) {
            range = textStr.range(str)
            while (range[0] >= 0 && range[1] <= tText().length) {
                span.setSpan(RelativeSizeSpan(size / startSize), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                typeFont?.apply {
                    span.setSpan(UITypefaceSpan(this), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                }
                span.setSpan(
                    ForegroundColorSpan(context.color(rColor)),
                    range[0],
                    range[1],
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                )
                range = textStr.range(inStr, range[1])
            }
        }
    }
    setText(span, TextView.BufferType.SPANNABLE)
}

fun TextView.highLightSentence(
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, @SuppressLint("ResourceType") @ColorRes rTextColor: Int = currentTextColor, @ColorRes rBGColor: Int = android.R.color.transparent
) {
    val textStr = tText().toLowerCase(Locale.getDefault())
    val inStr = input?.toLowerCase(Locale.getDefault())
    val strings = inStr?.trim()?.split("\\s+")
    val typeFont = context.font(fontPath)
    val startSize = textSize
    val span = SpannableString(tText())
    var range: IntArray
    strings?.apply {
        for (str in this) {
            range = textStr.range(str)
            while (range[0] >= 0 && range[1] <= tText().length) {
                span.setSpan(RelativeSizeSpan(size / startSize), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                typeFont?.apply {
                    span.setSpan(UITypefaceSpan(this), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                }
                span.setSpan(
                    UIRoundedBackgroundSpan(context.color(rBGColor), context.color(rTextColor)),
                    range[0],
                    range[1],
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                )
                range = textStr.range(inStr, range[1])
            }
        }
    }
    setText(span, TextView.BufferType.SPANNABLE)
}

fun TextView.highLightAndClick(
    action: () -> Unit,
    from: Int,
    to: Int,
    fontPath: String? = "",
    size: Float = textSize, @ColorRes rColor: Int = android.R.color.transparent
) {
    if (from >= 0 && to >= 0) {
        val typeFont = context.font(fontPath)
        val startSize = textSize
        val span = SpannableString(tText())
        span.setSpan(RelativeSizeSpan((size / startSize).toFloat()), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        typeFont?.apply {
            span.setSpan(UITypefaceSpan(this), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }
        span.setSpan(UIClickableSpan(action), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        span.setSpan(ForegroundColorSpan(context.color(rColor)), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        setText(span, TextView.BufferType.NORMAL)
        movementMethod = LinkMovementMethod.getInstance()
        highlightColor = Color.TRANSPARENT
    }
}

fun TextView.highLightAndClick(
    action: () -> Unit,
    from: Int,
    to: Int,
    fontPath: String? = "",
    size: Float = textSize, @SuppressLint("ResourceType") @ColorRes rTextColor: Int = currentTextColor, @ColorRes rBGColor: Int = android.R.color.transparent
) {
    if (from >= 0 && to >= 0) {
        val typeFont = context.font(fontPath)
        val startSize = textSize
        val span = SpannableString(tText())
        span.setSpan(RelativeSizeSpan((size / startSize).toFloat()), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        typeFont?.apply {
            span.setSpan(UITypefaceSpan(this), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }
        span.setSpan(UIClickableSpan(action), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        span.setSpan(UIRoundedBackgroundSpan( context.color(rBGColor),  context.color(rTextColor)), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        setText(span, TextView.BufferType.NORMAL)
        movementMethod = LinkMovementMethod.getInstance()
        highlightColor = Color.TRANSPARENT
    }
}

fun TextView.highLight(
    from: Int,
    to: Int,
    fontPath: String? = "",
    size: Float = textSize, @ColorRes rColor: Int = android.R.color.transparent
) {
    if (from >= 0 && to >= 0) {
        val typeFont = context.font(fontPath)
        val startSize = textSize
        val span = SpannableString(tText())
        span.setSpan(RelativeSizeSpan(size / startSize), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        typeFont?.apply {
            span.setSpan(UITypefaceSpan(this), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }
        span.setSpan(ForegroundColorSpan(context.color(rColor)), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        setText(span, TextView.BufferType.NORMAL)
    }
}

fun TextView.highLight(
    from: Int,
    to: Int,
    fontPath: String? = "",
    size: Float = textSize, @SuppressLint("ResourceType") @ColorRes rTextColor: Int = currentTextColor, @ColorRes rBGColor: Int = android.R.color.transparent
) {
    if (from >= 0 && to >= 0) {
        val typeFont = context.font(fontPath)
        val startSize = textSize
        val span = SpannableString(tText())
        span.setSpan(RelativeSizeSpan(size / startSize), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        typeFont?.apply {
            span.setSpan(UITypefaceSpan(this), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }
        span.setSpan(UIRoundedBackgroundSpan(context.color(rBGColor),  context.color(rTextColor)), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        setText(span, TextView.BufferType.NORMAL)
    }
}

/* highlight for String*/
fun TextView.highLightFirst(
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, color: String = ""
) {
    val range = tText().toLowerCase(Locale.getDefault()).range(input?.toLowerCase(Locale.getDefault()))
    highLight(range[0], range[1], fontPath, size, color)
}

fun TextView.highLightFirst(
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, textColor: String = "", bGColor: String = ""
) {
    val range = tText().range(input?.toLowerCase(Locale.getDefault()))
    highLight(range[0], range[1], fontPath, size, textColor, bGColor)
}

fun TextView.highLightFirstAndClick(
    action: () -> Unit,
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, color: String = ""
) {
    val range = tText().toLowerCase(Locale.getDefault()).range(input?.toLowerCase(Locale.getDefault()))
    highLightAndClick(action, range[0], range[1], fontPath, size, color)
}

fun TextView.highLightFirstAndClick(
    action: () -> Unit,
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, textColor: String = "", bGColor: String = ""
) {
    val range = tText().toLowerCase(Locale.getDefault()).range(input?.toLowerCase(Locale.getDefault()))
    highLightAndClick(action, range[0], range[1], fontPath, size, textColor, bGColor)
}

fun TextView.highLightLast(
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, color: String = ""
) {
    val range = tText().toLowerCase(Locale.getDefault()).rangeLast(input?.toLowerCase(Locale.getDefault()))
    highLight(range[0], range[1], fontPath, size, color)
}

fun TextView.highLightLast(
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, textColor: String = "", bGColor: String = ""
) {
    val range = tText().rangeLast(input?.toLowerCase(Locale.getDefault()))
    highLight(range[0], range[1], fontPath, size, textColor, bGColor)
}

fun TextView.highLight(
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, color: String = ""
) {
    var range = tText().toLowerCase(Locale.getDefault()).range(input?.toLowerCase(Locale.getDefault()))
    if (range[0] >= 0) {
        val typeFont = context.font(fontPath)
        val startSize = textSize
        val span = SpannableString(tText())
        while (range[0] >= 0 && range[1] <= tText().length) {
            span.setSpan(RelativeSizeSpan(size / startSize), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            typeFont?.apply {
                span.setSpan(UITypefaceSpan(this), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            }
            span.setSpan(
                ForegroundColorSpan(context.color(color) ?: android.R.color.transparent),
                range[0],
                range[1],
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            range = tText().toLowerCase(Locale.getDefault()).range(input?.toLowerCase(Locale.getDefault()), range[1])
        }
        setText(span, TextView.BufferType.NORMAL)
    }
}

fun TextView.highLight(
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, textColor: String = "", bGColor: String = ""
) {
    var range = tText().toLowerCase(Locale.getDefault()).range(input?.toLowerCase(Locale.getDefault()))
    if (range[0] >= 0) {
        val typeFont = context.font(fontPath)
        val startSize = textSize
        val span = SpannableString(tText())
        while (range[0] >= 0 && range[1] <= tText().length) {
            span.setSpan(RelativeSizeSpan(size / startSize), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            typeFont?.apply {
                span.setSpan(UITypefaceSpan(this), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            }
            span.setSpan(
                UIRoundedBackgroundSpan(context.color(bGColor) ?: android.R.color.transparent,  context.color(textColor) ?: currentTextColor),
                range[0],
                range[1],
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            range = tText().toLowerCase(Locale.getDefault()).range(input?.toLowerCase(Locale.getDefault()), range[1])
        }
        setText(span, TextView.BufferType.NORMAL)
    }
}

fun TextView.highLightSentence(
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, color: String = ""
) {
    val textStr = tText().toLowerCase(Locale.getDefault())
    val inStr = input?.toLowerCase(Locale.getDefault())
    val strings = inStr?.trim()?.split("\\s+")
    val typeFont = context.font(fontPath)
    val startSize = textSize
    val span = SpannableString(tText())
    var range: IntArray
    strings?.apply {
        for (str in this) {
            range = textStr.range(str)
            while (range[0] >= 0 && range[1] <= tText().length) {
                span.setSpan(RelativeSizeSpan(size / startSize), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                typeFont?.apply {
                    span.setSpan(UITypefaceSpan(this), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                }
                span.setSpan(
                    ForegroundColorSpan(context.color(color) ?: android.R.color.transparent),
                    range[0],
                    range[1],
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                )
                range = textStr.range(inStr, range[1])
            }
        }
    }
    setText(span, TextView.BufferType.SPANNABLE)
}


fun TextView.highLightSentence(
    input: String?,
    fontPath: String? = "",
    size: Float = textSize, textColor: String = "", bGColor: String = ""
) {
    val textStr = tText().toLowerCase(Locale.getDefault())
    val inStr = input?.toLowerCase(Locale.getDefault())
    val strings = inStr?.trim()?.split("\\s+")
    val typeFont = context.font(fontPath)
    val startSize = textSize
    val span = SpannableString(tText())
    var range: IntArray
    strings?.apply {
        for (str in this) {
            range = textStr.range(str)
            while (range[0] >= 0 && range[1] <= tText().length) {
                span.setSpan(RelativeSizeSpan(size / startSize), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                typeFont?.apply {
                    span.setSpan(UITypefaceSpan(this), range[0], range[1], Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                }
                span.setSpan(
                    UIRoundedBackgroundSpan(context.color(bGColor) ?: android.R.color.transparent,  context.color(textColor) ?: currentTextColor),
                    range[0],
                    range[1],
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                )
                range = textStr.range(inStr, range[1])
            }
        }
    }
    setText(span, TextView.BufferType.SPANNABLE)
}

fun TextView.highLightAndClick(
    action: () -> Unit,
    from: Int,
    to: Int,
    fontPath: String? = "",
    size: Float = textSize, color: String = ""
) {
    if (from >= 0 && to >= 0) {
        val typeFont = context.font(fontPath)
        val startSize = textSize
        val span = SpannableString(tText())
        span.setSpan(RelativeSizeSpan((size / startSize).toFloat()), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        typeFont?.apply {
            span.setSpan(UITypefaceSpan(this), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }
        span.setSpan(UIClickableSpan(action), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        span.setSpan(ForegroundColorSpan(context.color(color) ?: android.R.color.transparent), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        setText(span, TextView.BufferType.NORMAL)
        movementMethod = LinkMovementMethod.getInstance()
        highlightColor = Color.TRANSPARENT
    }
}

fun TextView.highLightAndClick(
    action: () -> Unit,
    from: Int,
    to: Int,
    fontPath: String? = "",
    size: Float = textSize, textColor: String = "", bGColor: String = ""
) {
    if (from >= 0 && to >= 0) {
        val typeFont = context.font(fontPath)
        val startSize = textSize
        val span = SpannableString(tText())
        span.setSpan(RelativeSizeSpan((size / startSize).toFloat()), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        typeFont?.apply {
            span.setSpan(UITypefaceSpan(this), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }
        span.setSpan(UIClickableSpan(action), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        span.setSpan(UIRoundedBackgroundSpan(context.color(bGColor) ?: android.R.color.transparent,  context.color(textColor) ?: currentTextColor), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        setText(span, TextView.BufferType.NORMAL)
        movementMethod = LinkMovementMethod.getInstance()
        highlightColor = Color.TRANSPARENT
    }
}

fun TextView.highLight(
    from: Int,
    to: Int,
    fontPath: String? = "",
    size: Float = textSize, color: String = ""
) {
    if (from >= 0 && to >= 0) {
        val typeFont = context.font(fontPath)
        val startSize = textSize
        val span = SpannableString(tText())
        span.setSpan(RelativeSizeSpan(size / startSize), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        typeFont?.apply {
            span.setSpan(UITypefaceSpan(this), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }
        span.setSpan(ForegroundColorSpan(context.color(color) ?: android.R.color.transparent), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        setText(span, TextView.BufferType.NORMAL)
    }
}

fun TextView.highLight(
    from: Int,
    to: Int,
    fontPath: String? = "",
    size: Float = textSize, textColor: String = "", bGColor: String = ""
) {
    if (from >= 0 && to >= 0) {
        val typeFont = context.font(fontPath)
        val startSize = textSize
        val span = SpannableString(tText())
        span.setSpan(RelativeSizeSpan(size / startSize), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        typeFont?.apply {
            span.setSpan(UITypefaceSpan(this), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }
        span.setSpan(UIRoundedBackgroundSpan(context.color(bGColor) ?: android.R.color.transparent,  context.color(textColor) ?: currentTextColor), from, to, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        setText(span, TextView.BufferType.NORMAL)
    }
}


fun TextView.gravityCenter() {
    gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
}

fun TextView.gravityBottom() {
    gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
}

@MainThread
fun EditText.cursorColor(@ColorRes rColor: Int) {
    try {
        val color = context.colorInt(rColor)
        // Get the cursor resource id
        var field = TextView::class.java.getDeclaredField("mCursorDrawableRes")
        field.isAccessible = true
        val drawableResId = field.getInt(this)

        // Get the editor
        field = TextView::class.java.getDeclaredField("mEditor")
        field.isAccessible = true
        val editor = field.get(this)

        // Get the drawable and set a color filter
        val drawable = ContextCompat.getDrawable(context, drawableResId)
        drawable?.apply {
            this.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            val drawables = arrayOf(this, this)

            // Set the drawables
            field = editor.javaClass.getDeclaredField("mCursorDrawable")
            field.isAccessible = true
            field.set(editor, drawables)
        }
    } catch (ignored: Exception) {
    }

}
