package com.app.core.ui.layout

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.support.annotation.IdRes
import android.support.constraint.ConstraintLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView

fun RelativeLayout.params(width: Int = Wrap, height: Int = Wrap) = RelativeLayout.LayoutParams(width, height)

fun <E : View> RelativeLayout.ui(
    view: E,
    init: (E.(RelativeLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap, height: Int = Wrap
): E {
    val p = RelativeLayout.LayoutParams(width, height)
    view.layoutParams = p
    init?.let { view.it(p) }
    this.addView(view)
    return view
}

fun <T : RelativeLayout, E : View> T.create(
    view: E,
    init: (E.(RelativeLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap, height: Int = Wrap
): E {
    val p = RelativeLayout.LayoutParams(width, height)
    view.layoutParams = p
    init?.let { view.it(p) }
    return view
}

fun <T : RelativeLayout> T.ui(init: T.() -> Unit) = init()

fun <T : Activity> T.relativeLayout(
    init: (RelativeLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): RelativeLayout {
    val view = RelativeLayout(this)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Fragment> T.relativeLayout(
    init: (RelativeLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): RelativeLayout {
    val view = RelativeLayout(this.context)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Dialog> T.relativeLayout(
    init: (RelativeLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): RelativeLayout {
    val view = RelativeLayout(this.context)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : ViewGroup> T.relativeLayout(
    init: (RelativeLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): RelativeLayout {
    val view = RelativeLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RelativeLayout.relativeLayout(
    init: (RelativeLayout.(RelativeLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): RelativeLayout {
    val view = RelativeLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun LinearLayout.relativeLayout(
    init: (RelativeLayout.(LinearLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): RelativeLayout {
    val view = RelativeLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun FrameLayout.relativeLayout(
    init: (RelativeLayout.(FrameLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): RelativeLayout {
    val view = RelativeLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun ConstraintLayout.relativeLayout(
    init: (RelativeLayout.(ConstraintLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): RelativeLayout {
    val view = RelativeLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CoordinatorLayout.relativeLayout(
    init: (RelativeLayout.(CoordinatorLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): RelativeLayout {
    val view = RelativeLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CollapsingToolbarLayout.relativeLayout(
    init: (RelativeLayout.(CollapsingToolbarLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): RelativeLayout {
    val view = RelativeLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RecyclerView.relativeLayout(
    init: (RelativeLayout.(RecyclerView.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): RelativeLayout {
    val view = RelativeLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

/************Properties**************/

fun <T : View> T.center() {
    if (layoutParams is RelativeLayout.LayoutParams) {
        val params = layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.CENTER_IN_PARENT, 1)
    }
}

fun <T : View> T.centerHorizontal() {
    if (layoutParams is RelativeLayout.LayoutParams) {
        val params = layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, 1)
    }
}

fun <T : View> T.centerVertical() {
    if (layoutParams is RelativeLayout.LayoutParams) {
        val params = layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.CENTER_VERTICAL, 1)
    }
}

fun <T : View> T.centerVerticalLeft() {
    if (layoutParams is RelativeLayout.LayoutParams) {
        val params = layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.CENTER_VERTICAL, 1)
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1)
    }
}

fun <T : View> T.centerVerticalRight() {
    if (layoutParams is RelativeLayout.LayoutParams) {
        val params = layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.CENTER_VERTICAL, 1)
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1)
    }
}

fun <T : View> T.left() {
    if (layoutParams is RelativeLayout.LayoutParams) {
        val params = layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1)
    }
}

fun <T : View> T.right() {
    if (layoutParams is RelativeLayout.LayoutParams) {
        val params = layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1)
    }
}

fun <T : View> T.top() {
    if (layoutParams is RelativeLayout.LayoutParams) {
        val params = layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 1)
    }
}

fun <T : View> T.bottom() {
    if (layoutParams is RelativeLayout.LayoutParams) {
        val params = layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1)
    }
}

fun <T : View> T.leftTop() {
    if (layoutParams is RelativeLayout.LayoutParams) {
        val params = layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1)
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 1)
    }
}

fun <T : View> T.leftBottom() {
    if (layoutParams is RelativeLayout.LayoutParams) {
        val params = layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1)
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1)
    }
}

fun <T : View> T.rightTop() {
    if (layoutParams is RelativeLayout.LayoutParams) {
        val params = layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1)
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 1)
    }
}

fun <T : View> T.rightBottom() {
    if (layoutParams is RelativeLayout.LayoutParams) {
        val params = layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1)
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1)
    }
}

fun <T : View> T.above(@IdRes idRes: Int?) {
    idRes?.let {
        if (layoutParams is RelativeLayout.LayoutParams) {
            val params = layoutParams as RelativeLayout.LayoutParams
            params.addRule(RelativeLayout.ABOVE, idRes)
        }
    }
}

fun <T : View> T.below(@IdRes idRes: Int?) {
    idRes?.let {
        if (layoutParams is RelativeLayout.LayoutParams) {
            val params = layoutParams as RelativeLayout.LayoutParams
            params.addRule(RelativeLayout.BELOW, idRes)
        }
    }
}

fun <T : View> T.leftOf(@IdRes idRes: Int?) {
    idRes?.let {
        if (layoutParams is RelativeLayout.LayoutParams) {
            val params = layoutParams as RelativeLayout.LayoutParams
            params.addRule(RelativeLayout.LEFT_OF, idRes)
        }
    }
}

fun <T : View> T.rightOf(@IdRes idRes: Int?) {
    idRes?.let {
        if (layoutParams is RelativeLayout.LayoutParams) {
            val params = layoutParams as RelativeLayout.LayoutParams
            params.addRule(RelativeLayout.RIGHT_OF, idRes)
        }
    }
}
