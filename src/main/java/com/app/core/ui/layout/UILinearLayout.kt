package com.app.core.ui.layout

import android.app.Activity
import android.app.Dialog
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

fun LinearLayout.params(width: Int = Wrap, height: Int = Wrap): LinearLayout.LayoutParams =
    LinearLayout.LayoutParams(width, height)

fun <T : LinearLayout, E : View> T.ui(
    view: E,
    init: (E.(LinearLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap, height: Int = Wrap
): E {
    val p = LinearLayout.LayoutParams(width, height)
    view.layoutParams = p
    init?.let { view.it(p) }
    this.addView(view)
    return view
}

fun <T : LinearLayout, E : View> T.create(
    view: E,
    init: (E.(LinearLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap, height: Int = Wrap
): E {
    val p = LinearLayout.LayoutParams(width, height)
    view.layoutParams = p
    init?.let { view.it(p) }
    return view
}

fun <T : LinearLayout> T.ui(init: T.() -> Unit) = init()

fun <T : Activity> T.linearLayout(
    init: (LinearLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): LinearLayout {
    val view = LinearLayout(this)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Activity> T.verticalLayout(
    init: (LinearLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): LinearLayout {
    val view = LinearLayout(this)
    view.layoutParams = params(width, height)
    view.orientation = LinearLayout.VERTICAL
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Activity> T.horizontalLayout(
    init: (LinearLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): LinearLayout {
    val view = LinearLayout(this)
    view.layoutParams = params(width, height)
    view.orientation = LinearLayout.HORIZONTAL
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Fragment> T.linearLayout(
    init: (LinearLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): LinearLayout {
    val view = LinearLayout(this.context)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Fragment> T.verticalLayout(
    init: (LinearLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): LinearLayout {
    val view = LinearLayout(this.context)
    view.layoutParams = params(width, height)
    view.orientation = LinearLayout.VERTICAL
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Fragment> T.horizontalLayout(
    init: (LinearLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): LinearLayout {
    val view = LinearLayout(this.context)
    view.layoutParams = params(width, height)
    view.orientation = LinearLayout.HORIZONTAL
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Dialog> T.linearLayout(
    init: (LinearLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): LinearLayout {
    val view = LinearLayout(this.context)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Dialog> T.verticalLayout(
    init: (LinearLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): LinearLayout {
    val view = LinearLayout(this.context)
    view.layoutParams = params(width, height)
    view.orientation = LinearLayout.VERTICAL
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Dialog> T.horizontalLayout(
    init: (LinearLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): LinearLayout {
    val view = LinearLayout(this.context)
    view.layoutParams = params(width, height)
    view.orientation = LinearLayout.HORIZONTAL
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : ViewGroup> T.linearLayout(
    init: (LinearLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun <T : ViewGroup> T.verticalLayout(
    init: (LinearLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    view.orientation = LinearLayout.VERTICAL
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun <T : ViewGroup> T.horizontalLayout(
    init: (LinearLayout.(ViewGroup.LayoutParams?) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    view.orientation = LinearLayout.HORIZONTAL
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RelativeLayout.linearLayout(
    init: (LinearLayout.(RelativeLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RelativeLayout.verticalLayout(
    init: (LinearLayout.(RelativeLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    view.orientation = LinearLayout.VERTICAL
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RelativeLayout.horizontalLayout(
    init: (LinearLayout.(RelativeLayout.LayoutParams?) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    view.orientation = LinearLayout.HORIZONTAL
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun LinearLayout.linearLayout(
    init: (LinearLayout.(LinearLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun LinearLayout.verticalLayout(
    init: (LinearLayout.(LinearLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    view.orientation = LinearLayout.VERTICAL
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun LinearLayout.horizontalLayout(
    init: (LinearLayout.(LinearLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    view.orientation = LinearLayout.HORIZONTAL
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun FrameLayout.linearLayout(
    init: (LinearLayout.(FrameLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun FrameLayout.verticalLayout(
    init: (LinearLayout.(FrameLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    view.orientation = LinearLayout.VERTICAL
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun FrameLayout.horizontalLayout(
    init: (LinearLayout.(FrameLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    view.orientation = LinearLayout.HORIZONTAL
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun ConstraintLayout.linearLayout(
    init: (LinearLayout.(ConstraintLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun ConstraintLayout.verticalLayout(
    init: (LinearLayout.(ConstraintLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    view.orientation = LinearLayout.VERTICAL
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun ConstraintLayout.horizontalLayout(
    init: (LinearLayout.(ConstraintLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    view.orientation = LinearLayout.HORIZONTAL
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CoordinatorLayout.linearLayout(
    init: (LinearLayout.(CoordinatorLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CoordinatorLayout.verticalLayout(
    init: (LinearLayout.(CoordinatorLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    view.orientation = LinearLayout.VERTICAL
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CoordinatorLayout.horizontalLayout(
    init: (LinearLayout.(CoordinatorLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    view.orientation = LinearLayout.HORIZONTAL
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CollapsingToolbarLayout.linearLayout(
    init: (LinearLayout.(CollapsingToolbarLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CollapsingToolbarLayout.verticalLayout(
    init: (LinearLayout.(CollapsingToolbarLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    view.orientation = LinearLayout.VERTICAL
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CollapsingToolbarLayout.horizontalLayout(
    init: (LinearLayout.(CollapsingToolbarLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    view.orientation = LinearLayout.HORIZONTAL
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RecyclerView.linearLayout(
    init: (LinearLayout.(RecyclerView.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RecyclerView.verticalLayout(
    init: (LinearLayout.(RecyclerView.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    view.orientation = LinearLayout.VERTICAL
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RecyclerView.horizontalLayout(
    init: (LinearLayout.(RecyclerView.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): LinearLayout {
    val view = LinearLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    view.orientation = LinearLayout.HORIZONTAL
    init?.let { view.it(params) }
    addView(view)
    return view
}