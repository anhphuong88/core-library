package com.app.core.ui.layout

import android.app.Activity
import android.app.Dialog
import android.content.Context
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

const val Match = ViewGroup.LayoutParams.MATCH_PARENT
const val Wrap = ViewGroup.LayoutParams.WRAP_CONTENT

fun <T : View> T.lparams(width: Int = Wrap, height: Int = Wrap): T {
    this.layoutParams = layoutParams?.let {
        when (it) {
            is RelativeLayout.LayoutParams -> RelativeLayout.LayoutParams(width, height)
            is LinearLayout.LayoutParams -> LinearLayout.LayoutParams(width, height)
            is FrameLayout.LayoutParams -> FrameLayout.LayoutParams(width, height)
            is CoordinatorLayout.LayoutParams -> CoordinatorLayout.LayoutParams(width, height)
            is ConstraintLayout.LayoutParams -> ConstraintLayout.LayoutParams(width, height)
            is RecyclerView.LayoutParams -> RecyclerView.LayoutParams(width, height)
            is CollapsingToolbarLayout.LayoutParams -> CollapsingToolbarLayout.LayoutParams(width, height)
            else -> ViewGroup.LayoutParams(width, height)
        }
    } ?: ViewGroup.LayoutParams(width, height)
    return this
}

fun View.params(width: Int = Wrap, height: Int = Wrap) = ViewGroup.LayoutParams(width, height)

fun Context.params(width: Int = Wrap, height: Int = Wrap): ViewGroup.LayoutParams =
    ViewGroup.LayoutParams(width, height)

fun <T : Dialog> T.params(
    width: Int = Wrap,
    height: Int = Wrap
): ViewGroup.LayoutParams = ViewGroup.LayoutParams(width, height)

fun <T : Fragment> T.params(
    width: Int = Wrap,
    height: Int = Wrap
): ViewGroup.LayoutParams = ViewGroup.LayoutParams(width, height)

fun <T : Activity> T.params(
    width: Int = Wrap,
    height: Int = Wrap
): ViewGroup.LayoutParams = ViewGroup.LayoutParams(width, height)

fun <T : Fragment, V : View> T.ui(init: T.() -> V): V {
    val view = init()
    if (view.layoutParams == null) view.layoutParams = view.params(Match, Match)
    return view
}

fun <T : Activity, V : View> T.ui(init: T.() -> V): V {
    val view = init()
    if (view.layoutParams == null) view.layoutParams = view.params(Match, Match)
    return view
}

fun <T : Dialog, V : View> T.ui(init: T.() -> V): V {
    val view = init()
    if (view.layoutParams == null) view.layoutParams = view.params(Match, Match)
    return view
}

fun <E : View> ViewGroup.ui(
    view: Context.() -> E, init: (E.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Wrap, height: Int = Wrap
): E {
    val v = context.view()
    val p = ViewGroup.LayoutParams(width, height)
    v.layoutParams = p
    init?.let { v.it(p) }
    this.addView(v)
    return v
}

fun <T : ViewGroup, E : View> T.create(
    view: Context.() -> E,
    init: (E.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): E {
    val v = context.view()
    val p = ViewGroup.LayoutParams(width, height)
    v.layoutParams = p
    init?.let { v.it(p) }
    return v
}
