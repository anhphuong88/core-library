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
import com.app.core.ui.recycler.BaseRecyclerView

fun RecyclerView.params(width: Int = Wrap, height: Int = Wrap) = RecyclerView.LayoutParams(width, height)

fun <T : RecyclerView, E : View> T.ui(
    view: E,
    init: (E.(RecyclerView.LayoutParams) -> Unit)? = null,
    width: Int = Wrap, height: Int = Wrap
): E {
    val p = RecyclerView.LayoutParams(width, height)
    view.layoutParams = p
    init?.let { view.it(p) }
    this.addView(view)
    return view
}

fun <T : RecyclerView, E : View> T.create(
    view: E,
    init: (E.(RecyclerView.LayoutParams) -> Unit)? = null,
    width: Int = Wrap, height: Int = Wrap
): E {
    val p = RecyclerView.LayoutParams(width, height)
    view.layoutParams = p
    init?.let { view.it(p) }
    return view
}

fun <T : RecyclerView> T.ui(init: T.() -> Unit) = init()

fun <T : Activity> T.recyclerView(
    init: (RecyclerView.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): RecyclerView {
    val view = BaseRecyclerView(this)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Fragment> T.recyclerView(
    init: (RecyclerView.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): RecyclerView {
    val view = BaseRecyclerView(context as Context)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Dialog> T.recyclerView(
    init: (RecyclerView.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): RecyclerView {
    val view = BaseRecyclerView(context)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : ViewGroup> T.recyclerView(
    init: (RecyclerView.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): RecyclerView {
    val view = BaseRecyclerView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RelativeLayout.recyclerView(
    init: (RecyclerView.(RelativeLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): RecyclerView {
    val view = BaseRecyclerView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun LinearLayout.recyclerView(
    init: (RecyclerView.(LinearLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): RecyclerView {
    val view = BaseRecyclerView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun FrameLayout.recyclerView(
    init: (RecyclerView.(FrameLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): RecyclerView {
    val view = BaseRecyclerView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun ConstraintLayout.recyclerView(
    init: (RecyclerView.(ConstraintLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): RecyclerView {
    val view = BaseRecyclerView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CoordinatorLayout.recyclerView(
    init: (RecyclerView.(CoordinatorLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): RecyclerView {
    val view = BaseRecyclerView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CollapsingToolbarLayout.recyclerView(
    init: (RecyclerView.(CollapsingToolbarLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): RecyclerView {
    val view = BaseRecyclerView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RecyclerView.recyclerView(
    init: (RecyclerView.(RecyclerView.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): RecyclerView {
    val view = BaseRecyclerView(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}
