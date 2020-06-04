package com.app.core.ui.layout

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout

fun <T : Activity> T.cardView(
    init: (CardView.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): CardView {
    val view = CardView(this)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Fragment> T.cardView(
    init: (CardView.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): CardView {
    val view = CardView(this.context as Context)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Dialog> T.cardView(
    init: (CardView.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): CardView {
    val view = CardView(this.context)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : ViewGroup> T.cardView(
    init: (CardView.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): CardView {
    val view = CardView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RelativeLayout.cardView(
    init: (CardView.(RelativeLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): CardView {
    val view = CardView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun LinearLayout.cardView(
    init: (CardView.(LinearLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): CardView {
    val view = CardView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun FrameLayout.cardView(
    init: (CardView.(FrameLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): CardView {
    val view = CardView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun ConstraintLayout.cardView(
    init: (CardView.(ConstraintLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): CardView {
    val view = CardView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CoordinatorLayout.cardView(
    init: (CardView.(CoordinatorLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): CardView {
    val view = CardView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CollapsingToolbarLayout.cardView(
    init: (CardView.(CollapsingToolbarLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): CardView {
    val view = CardView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RecyclerView.cardView(
    init: (CardView.(RecyclerView.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): CardView {
    val view = CardView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}
