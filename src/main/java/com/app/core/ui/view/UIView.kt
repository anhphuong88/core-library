package com.app.core.ui.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.support.annotation.*
import android.support.constraint.ConstraintLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.RecyclerView
import android.util.StateSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.app.core.action.Action1
import com.app.core.thread.UIThread
import com.app.core.ui.animation.UIAnimation


data class CGRECT(var x: Int, var y: Int, var width: Int, var height: Int)

@IdRes
fun <T : View> T.autoGenId(): Int {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        id = View.generateViewId()
    }
    return id
}

@ColorInt
fun View.themeColor(@AttrRes resId: Int): Int {
    return context.themeColor(resId)
}

fun View.font(path: String?): Typeface? {
    return context.font(path)
}

fun View.density(): Float {
    return context.density()
}

@Px
fun View.statusBarHeight(): Int {
    return context.statusBarHeight()
}

@Px
fun View.dip(dp: Float): Int {
    return context.dip(dp)
}

@Px
fun View.sip(sp: Float): Int {
    return context.sip(sp)
}

fun View.pid(px: Int): Float {
    return context.pid(px)
}

fun View.pis(px: Int): Float {
    return context.pis(px)
}

@Px
fun View.dimen(@DimenRes rDimen: Int): Int {
    return context.dimen(rDimen)
}

fun View.dimenFloat(@DimenRes dimenFloat: Int): Float {
    return context.dimenFloat(dimenFloat)
}

fun View.drawable(@DrawableRes rDrawable: Int): Drawable? {
    return context.drawable(rDrawable)
}

fun View.transparent(): Drawable {
    return context.transparent()
}

@ColorRes
fun View.transparentRes(): Int {
    return context.transparentRes()
}

fun View.colorDrawable(@ColorRes rColor: Int): Drawable {
    return context.colorDrawable(rColor)
}

@ColorInt
fun View.color(value: String?): Int? {
    return context.color(value)
}

@ColorInt
fun View.color(@ColorRes rColor: Int): Int {
    return context.color(rColor)
}

@ColorInt
fun View.colorInt(@ColorRes rColor: Int, theme: Resources.Theme? = null): Int {
    return context.colorInt(rColor, theme)
}

fun View.text(@StringRes resText: Int): String {
    return context.text(resText)
}


fun View.padding(@Px value: Int) {
    setPadding(value, value, value, value)
}

fun View.padding(@Px lr: Int, @Px tb: Int) {
    setPadding(lr, tb, lr, tb)
}

fun View.padding(@Px l: Int, @Px t: Int, @Px r: Int, @Px b: Int) {
    setPadding(l, t, r, b)
}

fun View.paddingLeft(@Px l: Int) {
    setPadding(l, paddingTop, paddingRight, paddingBottom)
}

fun View.paddingTop(@Px t: Int) {
    setPadding(paddingLeft, t, paddingRight, paddingBottom)
}

fun View.paddingRight(@Px r: Int) {
    setPadding(paddingLeft, paddingTop, r, paddingBottom)
}

fun View.paddingBottom(@Px b: Int) {
    setPadding(paddingLeft, paddingTop, paddingRight, b)
}

fun View.padDimen(@DimenRes rDimenLtrb: Int) {
    setPadding(
        dimen(rDimenLtrb), dimen(rDimenLtrb),
        dimen(rDimenLtrb), dimen(rDimenLtrb)
    )
}

fun View.padDimen(@DimenRes rDimenLr: Int, @DimenRes rDimenTb: Int) {
    setPadding(
        dimen(rDimenLr), dimen(rDimenTb),
        dimen(rDimenLr), dimen(rDimenTb)
    )
}

fun View.padDimen(@DimenRes rL: Int, @DimenRes rT: Int, @DimenRes rR: Int, @DimenRes rB: Int) {
    setPadding(
        dimen(rL), dimen(rT),
        dimen(rR), dimen(rB)
    )
}

fun View.padDimenLeft(@DimenRes rL: Int) {
    setPadding(dimen(rL), paddingTop, paddingRight, paddingBottom)
}

fun View.padDimenTop(@DimenRes rT: Int) {
    setPadding(paddingLeft, dimen(rT), paddingRight, paddingBottom)
}

fun View.padDimenRight(@DimenRes rR: Int) {
    setPadding(paddingLeft, paddingTop, dimen(rR), paddingBottom)
}

fun View.padDimenBottom(@DimenRes rB: Int) {
    setPadding(paddingLeft, paddingTop, paddingRight, dimen(rB))
}

fun View.margin(@Px l: Int, @Px t: Int, @Px r: Int, @Px b: Int) {
    val v = layoutParams
    if (v is RelativeLayout.LayoutParams) {
        v.setMargins(l, t, r, b)
    } else if (v is LinearLayout.LayoutParams) {
        v.setMargins(l, t, r, b)
    } else if (v is FrameLayout.LayoutParams) {
        v.setMargins(l, t, r, b)
    } else if (v is ConstraintLayout.LayoutParams) {
        v.setMargins(l, t, r, b)
    } else if (v is RecyclerView.LayoutParams) {
        v.setMargins(l, t, r, b)
    } else if (v is CoordinatorLayout.LayoutParams) {
        v.setMargins(l, t, r, b)
    }
}

fun View.margin(@Px lr: Int, @Px tb: Int) {
    val v = layoutParams
    if (v is RelativeLayout.LayoutParams) {
        v.setMargins(lr, tb, lr, tb)
    } else if (v is LinearLayout.LayoutParams) {
        v.setMargins(lr, tb, lr, tb)
    } else if (v is FrameLayout.LayoutParams) {
        v.setMargins(lr, tb, lr, tb)
    } else if (v is ConstraintLayout.LayoutParams) {
        v.setMargins(lr, tb, lr, tb)
    } else if (v is RecyclerView.LayoutParams) {
        v.setMargins(lr, tb, lr, tb)
    } else if (v is CoordinatorLayout.LayoutParams) {
        v.setMargins(lr, tb, lr, tb)
    }
}

fun View.margin(@Px value: Int) {
    val v = layoutParams
    if (v is RelativeLayout.LayoutParams) {
        v.setMargins(value, value, value, value)
    } else if (v is LinearLayout.LayoutParams) {
        v.setMargins(value, value, value, value)
    } else if (v is FrameLayout.LayoutParams) {
        v.setMargins(value, value, value, value)
    } else if (v is ConstraintLayout.LayoutParams) {
        v.setMargins(value, value, value, value)
    } else if (v is RecyclerView.LayoutParams) {
        v.setMargins(value, value, value, value)
    } else if (v is CoordinatorLayout.LayoutParams) {
        v.setMargins(value, value, value, value)
    }
}

fun View.marginLeft(@Px value: Int) {
    val v = layoutParams
    if (v is RelativeLayout.LayoutParams) {
        v.setMargins(
            value, v.topMargin,
            v.rightMargin,
            v.bottomMargin
        )
    } else if (v is LinearLayout.LayoutParams) {
        v.setMargins(
            value, v.topMargin,
            v.rightMargin,
            v.bottomMargin
        )
    } else if (v is FrameLayout.LayoutParams) {
        v.setMargins(
            value, v.topMargin,
            v.rightMargin,
            v.bottomMargin
        )
    }else if (v is ConstraintLayout.LayoutParams) {
        v.setMargins(
            value, v.topMargin,
            v.rightMargin,
            v.bottomMargin
        )
    } else if (v is RecyclerView.LayoutParams) {
        v.setMargins(
            value, v.topMargin,
            v.rightMargin,
            v.bottomMargin
        )
    } else if (v is CoordinatorLayout.LayoutParams) {
        v.setMargins(
            value, v.topMargin,
            v.rightMargin,
            v.bottomMargin
        )
    }
}

fun View.marginTop(@Px value: Int) {
    val v = layoutParams
    if (v is RelativeLayout.LayoutParams) {
        v.setMargins(
            v.leftMargin, value,
            v.rightMargin,
            v.bottomMargin
        )
    } else if (v is LinearLayout.LayoutParams) {
        v.setMargins(
            v.leftMargin, value,
            v.rightMargin,
            v.bottomMargin
        )
    } else if (v is FrameLayout.LayoutParams) {
        v.setMargins(
            v.leftMargin, value,
            v.rightMargin,
            v.bottomMargin
        )
    } else if (v is ConstraintLayout.LayoutParams) {
        v.setMargins(
            v.leftMargin, value,
            v.rightMargin,
            v.bottomMargin
        )
    } else if (v is RecyclerView.LayoutParams) {
        v.setMargins(
            v.leftMargin, value,
            v.rightMargin,
            v.bottomMargin
        )
    } else if (v is CoordinatorLayout.LayoutParams) {
        v.setMargins(
            v.leftMargin, value,
            v.rightMargin,
            v.bottomMargin
        )
    }
}

fun View.marginRight(@Px value: Int) {
    val v = layoutParams
    if (v is RelativeLayout.LayoutParams) {
        v.setMargins(
            v.leftMargin,
            v.topMargin,
            value,
            v.bottomMargin
        )
    } else if (v is LinearLayout.LayoutParams) {
        v.setMargins(
            v.leftMargin,
            v.topMargin,
            value,
            v.bottomMargin
        )
    } else if (v is FrameLayout.LayoutParams) {
        v.setMargins(
            v.leftMargin,
            v.topMargin,
            value,
            v.bottomMargin
        )
    } else if (v is ConstraintLayout.LayoutParams) {
        v.setMargins(
            v.leftMargin,
            v.topMargin,
            value,
            v.bottomMargin
        )
    } else if (v is RecyclerView.LayoutParams) {
        v.setMargins(
            v.leftMargin,
            v.topMargin,
            value,
            v.bottomMargin
        )
    } else if (v is CoordinatorLayout.LayoutParams) {
        v.setMargins(
            v.leftMargin,
            v.topMargin,
            value,
            v.bottomMargin
        )
    }
}

fun View.marginBottom(@Px value: Int) {
    val v = layoutParams
    if (v is RelativeLayout.LayoutParams) {
        v.setMargins(
            v.leftMargin,
            v.topMargin,
            v.rightMargin,
            value
        )
    } else if (v is LinearLayout.LayoutParams) {
        v.setMargins(
            v.leftMargin,
            v.topMargin,
            v.rightMargin,
            value
        )
    } else if (v is FrameLayout.LayoutParams) {
        v.setMargins(
            v.leftMargin,
            v.topMargin,
            v.rightMargin,
            value
        )
    } else if (v is ConstraintLayout.LayoutParams) {
        v.setMargins(
            v.leftMargin,
            v.topMargin,
            v.rightMargin,
            value
        )
    } else if (v is RecyclerView.LayoutParams) {
        v.setMargins(
            v.leftMargin,
            v.topMargin,
            v.rightMargin,
            value
        )
    } else if (v is CoordinatorLayout.LayoutParams) {
        v.setMargins(
            v.leftMargin,
            v.topMargin,
            v.rightMargin,
            value
        )
    }
}

fun View.marginDimen(@DimenRes rL: Int, @DimenRes rT: Int, @DimenRes rR: Int, @DimenRes rB: Int) {
    margin(
        dimen(rL), dimen(rT),
        dimen(rR), dimen(rB)
    )
}

fun View.marginDimen(@DimenRes rLr: Int, @DimenRes rTb: Int) {
    margin(dimen(rLr), dimen(rTb))
}

fun View.marginDimen(@DimenRes rLtrb: Int) {
    margin(dimen(rLtrb))
}

fun View.marginDimenLeft(@DimenRes rValue: Int) {
    marginLeft(dimen(rValue))
}

fun View.marginDimenTop(@DimenRes rValue: Int) {
    marginTop(dimen(rValue))
}

fun View.marginDimenRight(@DimenRes rValue: Int) {
    marginRight(dimen(rValue))
}

fun View.marginDimenBottom(@DimenRes rValue: Int) {
    marginBottom(dimen(rValue))
}

fun View.circle(@ColorRes rColor: Int, border: Int = 0, @ColorRes rBorderColor: Int = android.R.color.transparent): Drawable {
    return context.circle(rColor, border, rBorderColor)
}

fun View.circle(color:  String? = "", border: Int = 0, borderColor: String? = ""): Drawable {
    return context.circle(color, border, borderColor)
}

fun View.circle(@ColorRes rColor: Int, border: Int = 0, @ColorRes rBorderColor: Int = android.R.color.transparent, @ColorRes rSelectedBorderColor: Int = android.R.color.transparent): StateListDrawable {
    return context.circle(rColor, border, rBorderColor, rSelectedBorderColor)
}

fun View.circle(@ColorRes rColor: Int, @ColorRes rSelectedColor: Int, border: Int = 0, @ColorRes rBorderColor: Int = android.R.color.transparent, @ColorRes rSelectedBorderColor: Int = android.R.color.transparent): StateListDrawable {
    return context.circle(rColor, rSelectedColor, border, rBorderColor, rSelectedBorderColor)
}

fun View.circle(color:  String? = "", border: Int = 0, borderColor: String? = "", selectedBorderColor: String? = ""): StateListDrawable {
    return context.circle(color, border, borderColor, selectedBorderColor)
}

fun View.circle(color:  String? = "", selectedColor: String? = "", border: Int = 0, borderColor: String? = "", selectedBorderColor: String? = ""): StateListDrawable {
    return context.circle(color, selectedColor, border, borderColor, selectedBorderColor)
}

fun View.circleGradient(@ColorRes startColor: Int, @ColorRes endColor: Int, orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.BOTTOM_TOP): Drawable {
    return context.circleGradient(startColor, endColor, orientation)
}

fun View.circleGradient(
    rStartColor: String? = "",
    rEndColor: String? = "",
    orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.BOTTOM_TOP
): Drawable {
    return context.circleGradient(rStartColor, rEndColor, orientation)
}
fun View.circleShadow(startShadowColor: String? = "", endShadowColor: String? = "", color: String? = "", selectedColor: String? = "" , elevation: Float = 0f, border: Int = 0, borderColor: String? = "", selectedBorderColor: String? = ""): Drawable {
    return context.circleShadow(startShadowColor, endShadowColor, color, selectedColor, elevation, border, borderColor, selectedBorderColor)
}

fun View.squareGradient(
    @ColorRes rStartColor: Int, @ColorRes rEndColor: Int, radius: Float = 0f,
    border: Int = 0, @ColorRes rBorderColor: Int = android.R.color.transparent,
    orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.BOTTOM_TOP
): Drawable {
    return context.squareGradient(rStartColor, rEndColor, radius, border, rBorderColor, orientation)
}

fun View.squareGradient(
    rStartColor: String?, rEndColor: String?, radius: Float = 0f,
    border: Int = 0, @ColorRes rBorderColor: Int = android.R.color.transparent,
    orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.BOTTOM_TOP, angle: Float = 90f
): Drawable {
    return context.squareGradient(rStartColor, rEndColor, radius, border, rBorderColor, orientation, angle)
}

fun View.square(
    color: String? = "", radius: Float = 0f,
    border: Int = 0, borderColor: String? = ""
): Drawable {
    return context.square(color, radius, border, borderColor)
}

fun View.square(
    color: String? = "", radius: Float = 0f,
    border: Int = 0, borderColor: String? = "", selectedBorderColor: String? = ""
): StateListDrawable {
    return context.square(color, radius, border, borderColor, selectedBorderColor)
}
fun View.square(
    color: String? = "", selectedColor: String? = "", radius: Float = 0f,
    border: Int = 0, borderColor: String? = "", selectedBorderColor: String? = ""
): StateListDrawable {
    return  context.square(color, selectedColor,radius, border, borderColor, selectedBorderColor)
}

fun View.squareShadow(startShadowColor: String? = "", endShadowColor: String? = "", color: String? = "", selectedColor: String? = "" ,radius: Float = 0f, elevation: Float = 0f): Drawable {
    return context.squareShadow(startShadowColor, endShadowColor, color, selectedColor, radius, elevation)
}

fun View.square(
    @ColorRes rColor: Int, radius: Float = 0f,
    border: Int = 0, @ColorRes rBorderColor: Int = android.R.color.transparent
): Drawable {
    return context.square(rColor, radius, border, rBorderColor)
}

fun View.square(
    @ColorRes rColor: Int, radius: Float = 0f,
    border: Int = 0, @ColorRes rBorderColor: Int = android.R.color.transparent, @ColorRes rSelectedBorderColor: Int = android.R.color.transparent
): StateListDrawable {
    return context.square(rColor, radius, border, rBorderColor, rSelectedBorderColor)
}

fun View.square(
    @ColorRes rColor: Int, @ColorRes rSelectedColor: Int, radius: Float = 0f,
    border: Int = 0, @ColorRes rBorderColor: Int = android.R.color.transparent, @ColorRes rSelectedBorderColor: Int = android.R.color.transparent
): StateListDrawable {
    return context.square(rColor, rSelectedColor, radius, border, rBorderColor, rSelectedBorderColor)
}

fun View.circleImage(bitmap: Bitmap, size: Int, @ColorRes rColor: Int = android.R.color.transparent): Bitmap {
    return context.circleImage(bitmap, size, rColor)
}

fun View.circleWithText(
    size: Int, @ColorRes rCircleColor: Int,
    text: String,
    sizeText: Int, @ColorRes rColorText: Int = android.R.color.white,
    typeface: Typeface = Typeface.SANS_SERIF
): Drawable {
    return context.circleWithText(size, rCircleColor, text, sizeText, rColorText, typeface)
}

fun View.squareWithText(
    @ColorRes rCircleColor: Int,
    text: String,
    sizeText: Int, @ColorRes rColorText: Int = android.R.color.white,
    typeface: Typeface = Typeface.SANS_SERIF
): Drawable {
    return context.squareWithText(rCircleColor, text, sizeText, rColorText, typeface)
}

fun View.line(
    width: Int,
    height: Int,
    borderWidthInPixel: Int = 0, @ColorRes rColor: Int = android.R.color.transparent, @ColorRes rColorBorder: Int = android.R.color.white
): Drawable {
    return context.line(width, height, borderWidthInPixel, rColor, rColorBorder)
}

fun View.squareDualSide(
    width: Int,
    height: Int,
    borderWidthInPixel: Int = 0, @ColorRes rColor: Int = android.R.color.transparent, @ColorRes rColorBorder: Int = android.R.color.white
): Drawable {
    return context.squareDualSide(width, height, borderWidthInPixel, rColor, rColorBorder)
}


fun View.backgroundColor(@ColorRes rColor: Int) {
    setBackgroundColor(color(rColor))
}

fun View.backgroundColor(value: String?) {
    color(value)?.let {
        setBackgroundColor(it)
    }
}

fun View.backgroundTransparent() {
    setBackgroundColor(color(android.R.color.transparent))
}

@Suppress("DEPRECATION")
fun View.background(@DrawableRes rDrawable: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        background = drawable(rDrawable)
    } else {
        setBackgroundDrawable(drawable(rDrawable))
    }
}

@Suppress("DEPRECATION")
fun View.background(drawable: Drawable?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        background = drawable
    } else {
        setBackgroundDrawable(drawable)
    }
}

@Suppress("DEPRECATION")
fun <T : View> T.background(create: T.() -> Drawable?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        background = create()
    } else {
        setBackgroundDrawable(create())
    }
}

fun View.isPointInsideView(x: Float, y: Float): Boolean {
    val location = IntArray(2)
    getLocationOnScreen(location)
    val viewX = location[0]
    val viewY = location[1]

    return x >= viewX && x <= viewX + width && y >= viewY && y <= viewY + height
}

fun View.bound(): CGRECT {
    return CGRECT(left, top, left + width, top + height)
}

fun View.boundRoot(): CGRECT {
    var lef = left()
    var to = top()
    return CGRECT(lef, to, lef + width, to + height)
}

private fun View.left(): Int {
    return if (parent === rootView) left else left + (parent as View).left()
}

private fun View.top(): Int {
    return if (parent === rootView) top else top + (parent as View).top()
}


fun View.scaleIn(result: Action1<View>?) {
    alpha = 1f
    scaleX = 1f
    scaleY = 1f
    animate().alpha(0f).scaleX(0f).scaleY(0f).setListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {

        }

        override fun onAnimationEnd(animation: Animator) {
            result?.invoke(this@scaleIn)
        }

        override fun onAnimationCancel(animation: Animator) {
            result?.invoke(this@scaleIn)
        }

        override fun onAnimationRepeat(animation: Animator) {

        }
    }).duration = UIAnimation.animTime
}

fun View.scaleOut(result: Action1<View>?) {
    alpha = 0f
    scaleX = 0f
    scaleY = 0f
    animate().alpha(1f).scaleX(1f).scaleY(1f).setListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {

        }

        override fun onAnimationEnd(animation: Animator) {
            result?.invoke(this@scaleOut)
        }

        override fun onAnimationCancel(animation: Animator) {
            result?.invoke(this@scaleOut)
        }

        override fun onAnimationRepeat(animation: Animator) {

        }
    }).duration = UIAnimation.animTime
}

fun View.scaleOutIn(result: Action1<View>) {
    alpha = 0f
    scaleX = 0f
    scaleY = 0f
    animate().alpha(1f).scaleX(1f).scaleY(1f).setListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {

        }

        override fun onAnimationEnd(animation: Animator) {
            UIThread.run({ scaleIn(result) }, UIAnimation.animTime)
        }

        override fun onAnimationCancel(animation: Animator) {
            UIThread.run({ scaleIn(result) }, UIAnimation.animTime)
        }

        override fun onAnimationRepeat(animation: Animator) {

        }
    }).duration = UIAnimation.animTime
}

fun View.hide(isHide: Boolean = true, duration: Long = 300) {
    measure(measuredWidth, measuredHeight)
    val anim = if (isHide) ValueAnimator.ofInt(measuredHeight, 0) else ValueAnimator.ofInt(0, measuredHeight)
    anim.addUpdateListener {
        val value: Int = it.animatedValue as Int
        this.layoutParams.height = value
        this.layoutParams = layoutParams
    }
    anim.setDuration(duration);
    anim.start();
}


fun View.multipleScreen(xlarge: (() -> Unit)? = null, large: (() -> Unit)? = null, normal: (() -> Unit)? = null, small: (() -> Unit)? = null, default: (() -> Unit)? = null) {
    when (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
        Configuration.SCREENLAYOUT_SIZE_LARGE -> {
            large?.invoke() ?: normal?.invoke() ?: small?.invoke() ?: default?.invoke()
        }
        Configuration.SCREENLAYOUT_SIZE_NORMAL -> {
            normal?.invoke() ?: small?.invoke() ?: default?.invoke()
        }
        Configuration.SCREENLAYOUT_SIZE_SMALL -> {
            small?.invoke() ?: default?.invoke()
        }
        Configuration.SCREENLAYOUT_SIZE_XLARGE -> {
            xlarge?.invoke() ?: large?.invoke() ?: normal?.invoke() ?: small?.invoke() ?: default?.invoke()
        }
        else -> {
            default?.invoke()
        }
    }
}
