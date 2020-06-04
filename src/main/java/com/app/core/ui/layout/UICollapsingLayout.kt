package com.app.core.ui.layout

import android.app.Activity
import android.app.Dialog
import android.support.constraint.ConstraintLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout

fun CollapsingToolbarLayout.params(width: Int = Wrap, height: Int = Wrap) =
    CollapsingToolbarLayout.LayoutParams(width, height)

fun <E : View> CollapsingToolbarLayout.collapseUI(
    view: E,
    init: (E.(CollapsingToolbarLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap, height: Int = Wrap
): E {
    val p = CollapsingToolbarLayout.LayoutParams(width, height)
    view.layoutParams = p
    init?.let { view.it(p) }
    this.addView(view)
    return view
}

fun <E : View> CollapsingToolbarLayout.collapseCreate(
    view: E,
    init: (E.(CollapsingToolbarLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap, height: Int = Wrap
): E {
    val p = CollapsingToolbarLayout.LayoutParams(width, height)
    view.layoutParams = p
    init?.let { view.it(p) }
    return view
}

fun CollapsingToolbarLayout.ui(init: CollapsingToolbarLayout.() -> Unit) = init()

fun <T : Activity> T.collapsingToolbarLayout(
    init: (CollapsingToolbarLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): CollapsingToolbarLayout {
    val view = CollapsingToolbarLayout(this)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Fragment> T.collapsingToolbarLayout(
    init: (CollapsingToolbarLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): CollapsingToolbarLayout {
    val view = CollapsingToolbarLayout(context)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Dialog> T.collapsingToolbarLayout(
    init: (CollapsingToolbarLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): CollapsingToolbarLayout {
    val view = CollapsingToolbarLayout(context)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : ViewGroup> T.collapsingToolbarLayout(
    init: (CollapsingToolbarLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): CollapsingToolbarLayout {
    val view = CollapsingToolbarLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RelativeLayout.collapsingToolbarLayout(
    init: (CollapsingToolbarLayout.(RelativeLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): CollapsingToolbarLayout {
    val view = CollapsingToolbarLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun LinearLayout.collapsingToolbarLayout(
    init: (CollapsingToolbarLayout.(LinearLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): CollapsingToolbarLayout {
    val view = CollapsingToolbarLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun FrameLayout.collapsingToolbarLayout(
    init: (CollapsingToolbarLayout.(FrameLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): CollapsingToolbarLayout {
    val view = CollapsingToolbarLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun ConstraintLayout.collapsingToolbarLayout(
    init: (CollapsingToolbarLayout.(ConstraintLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): CollapsingToolbarLayout {
    val view = CollapsingToolbarLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CoordinatorLayout.collapsingToolbarLayout(
    init: (CollapsingToolbarLayout.(CoordinatorLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): CollapsingToolbarLayout {
    val view = CollapsingToolbarLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CollapsingToolbarLayout.collapsingToolbarLayout(
    init: (CollapsingToolbarLayout.(CollapsingToolbarLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): CollapsingToolbarLayout {
    val view = CollapsingToolbarLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RecyclerView.collapsingToolbarLayout(
    init: (CollapsingToolbarLayout.(RecyclerView.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): CollapsingToolbarLayout {
    val view = CollapsingToolbarLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}