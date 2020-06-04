package com.app.core.ui.layout

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.support.constraint.ConstraintLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView

fun FrameLayout.params(width: Int = Wrap, height: Int = Wrap) = FrameLayout.LayoutParams(width, height)

fun <T : FrameLayout, E : View> T.ui(
    view: E,
    init: (E.(FrameLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap, height: Int = Wrap
): E {
    val p = FrameLayout.LayoutParams(width, height)
    view.layoutParams = p
    init?.let { view.it(p) }
    this.addView(view)
    return view
}

fun <T : FrameLayout, E : View> T.create(
    view: E,
    init: (E.(FrameLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap, height: Int = Wrap
): E {
    val p = FrameLayout.LayoutParams(width, height)
    view.layoutParams = p
    init?.let { view.it(p) }
    return view
}

fun <T : FrameLayout> T.ui(init: T.() -> Unit) = init()

fun <T : Activity> T.frameLayout(
    init: (FrameLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): FrameLayout {
    val view = FrameLayout(this)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Fragment> T.frameLayout(
    init: (FrameLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): FrameLayout {
    val view = FrameLayout(context as Context)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Dialog> T.frameLayout(
    init: (FrameLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): FrameLayout {
    val view = FrameLayout(this.context)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : ViewGroup> T.frameLayout(
    init: (FrameLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): FrameLayout {
    val view = FrameLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RelativeLayout.frameLayout(
    init: (FrameLayout.(RelativeLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): FrameLayout {
    val view = FrameLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun LinearLayout.frameLayout(
    init: (FrameLayout.(LinearLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): FrameLayout {
    val view = FrameLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun FrameLayout.frameLayout(
    init: (FrameLayout.(FrameLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): FrameLayout {
    val view = FrameLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun ConstraintLayout.frameLayout(
    init: (FrameLayout.(ConstraintLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): FrameLayout {
    val view = FrameLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CoordinatorLayout.frameLayout(
    init: (FrameLayout.(CoordinatorLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): FrameLayout {
    val view = FrameLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CollapsingToolbarLayout.frameLayout(
    init: (FrameLayout.(CollapsingToolbarLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): FrameLayout {
    val view = FrameLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RecyclerView.frameLayout(
    init: (FrameLayout.(RecyclerView.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): FrameLayout {
    val view = FrameLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}
