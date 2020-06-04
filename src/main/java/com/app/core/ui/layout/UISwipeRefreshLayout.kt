package com.app.core.ui.layout


import android.app.Activity
import android.app.Dialog
import android.support.constraint.ConstraintLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout

fun SwipeRefreshLayout.params(width: Int = Wrap, height: Int = Wrap) = ViewGroup.LayoutParams(width, height)

fun <T : SwipeRefreshLayout, E : View> T.ui(
    view: E,
    init: (E.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Wrap, height: Int = Wrap
): E {
    val p = ViewGroup.LayoutParams(width, height)
    view.layoutParams = p
    init?.let { view.it(p) }
    this.addView(view)
    return view
}

fun <T : SwipeRefreshLayout, E : View> T.create(
    view: E,
    init: (E.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Wrap, height: Int = Wrap
): E {
    val p = ViewGroup.LayoutParams(width, height)
    view.layoutParams = p
    init?.let { view.it(p) }
    return view
}

fun <T : SwipeRefreshLayout> T.ui(init: T.() -> Unit) = init()

fun <T : Activity> T.swipeRefreshLayout(
    init: (SwipeRefreshLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): SwipeRefreshLayout {
    val view = SwipeRefreshLayout(this)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Fragment> T.swipeRefreshLayout(
    init: (SwipeRefreshLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): SwipeRefreshLayout {
    val view = this.context?.let { SwipeRefreshLayout(it) }
    view!!.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Dialog> T.swipeRefreshLayout(
    init: (SwipeRefreshLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): SwipeRefreshLayout {
    val view = SwipeRefreshLayout(this.context)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun SwipeRefreshLayout.swipeRefreshLayout(
    init: (SwipeRefreshLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): SwipeRefreshLayout {
    val view = SwipeRefreshLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun <T : ViewGroup> T.swipeRefreshLayout(
    init: (SwipeRefreshLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): SwipeRefreshLayout {
    val view = SwipeRefreshLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RelativeLayout.swipeRefreshLayout(
    init: (SwipeRefreshLayout.(RelativeLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): SwipeRefreshLayout {
    val view = SwipeRefreshLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun LinearLayout.swipeRefreshLayout(
    init: (SwipeRefreshLayout.(LinearLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): SwipeRefreshLayout {
    val view = SwipeRefreshLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun FrameLayout.swipeRefreshLayout(
    init: (SwipeRefreshLayout.(FrameLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): SwipeRefreshLayout {
    val view = SwipeRefreshLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun ConstraintLayout.swipeRefreshLayout(
    init: (SwipeRefreshLayout.(ConstraintLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): SwipeRefreshLayout {
    val view = SwipeRefreshLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CoordinatorLayout.swipeRefreshLayout(
    init: (SwipeRefreshLayout.(CoordinatorLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): SwipeRefreshLayout {
    val view = SwipeRefreshLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CollapsingToolbarLayout.swipeRefreshLayout(
    init: (SwipeRefreshLayout.(CollapsingToolbarLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): SwipeRefreshLayout {
    val view = SwipeRefreshLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}
