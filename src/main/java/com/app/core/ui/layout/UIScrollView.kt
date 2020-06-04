package com.app.core.ui.layout

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.support.constraint.ConstraintLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView

fun <T : Activity> T.scrollView(
    init: (ScrollView.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): ScrollView {
    val view = ScrollView(this)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Fragment> T.scrollView(
    init: (ScrollView.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): ScrollView {
    val view = ScrollView(this.context as Context)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Dialog> T.scrollView(
    init: (ScrollView.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): ScrollView {
    val view = ScrollView(this.context)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : ViewGroup> T.scrollView(
    init: (ScrollView.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): ScrollView {
    val view = ScrollView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RelativeLayout.scrollView(
    init: (ScrollView.(RelativeLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): ScrollView {
    val view = ScrollView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun LinearLayout.scrollView(
    init: (ScrollView.(LinearLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): ScrollView {
    val view = ScrollView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun FrameLayout.scrollView(
    init: (ScrollView.(FrameLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): ScrollView {
    val view = ScrollView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun ConstraintLayout.scrollView(
    init: (ScrollView.(ConstraintLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): ScrollView {
    val view = ScrollView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CoordinatorLayout.scrollView(
    init: (ScrollView.(CoordinatorLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): ScrollView {
    val view = ScrollView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CollapsingToolbarLayout.scrollView(
    init: (ScrollView.(CollapsingToolbarLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): ScrollView {
    val view = ScrollView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RecyclerView.scrollView(
    init: (ScrollView.(RecyclerView.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): ScrollView {
    val view = ScrollView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}
