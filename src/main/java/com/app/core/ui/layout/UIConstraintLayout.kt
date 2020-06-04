package com.app.core.ui.layout

import android.app.ActionBar
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


fun ConstraintLayout.params(width: Int = Wrap, height: Int = Wrap) = ConstraintLayout.LayoutParams(width, height)

fun ConstraintLayout.parentId() = ConstraintLayout.LayoutParams.PARENT_ID

fun <T : ConstraintLayout, E : View> T.ui(
    view: E,
    init: (E.(ConstraintLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap, height: Int = Wrap
): E {
    val p = ConstraintLayout.LayoutParams(width, height)
    view.layoutParams = p
    init?.let { view.it(p) }
    this.addView(view)
    return view
}

fun <T : ConstraintLayout, E : View> T.create(
    view: E,
    init: (E.(ConstraintLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap, height: Int = Wrap
): E {
    val p = ConstraintLayout.LayoutParams(width, height)
    view.layoutParams = p
    init?.let { view.it(p) }
    return view
}

fun <T : ConstraintLayout> T.ui(init: T.() -> Unit) = init()

fun <T : Activity> T.constraintLayout(
    init: (ConstraintLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): ConstraintLayout {
    val view = ConstraintLayout(this)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Fragment> T.constraintLayout(
    init: (ConstraintLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): ConstraintLayout {
    val view = ConstraintLayout(context)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : Dialog> T.constraintLayout(
    init: (ConstraintLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Match,
    height: Int = Match
): ConstraintLayout {
    val view = ConstraintLayout(context)
    view.layoutParams = params(width, height)
    init?.let { view.it(view.layoutParams) }
    return view
}

fun <T : ViewGroup> T.constraintLayout(
    init: (ConstraintLayout.(ViewGroup.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): ConstraintLayout {
    val view = ConstraintLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RelativeLayout.constraintLayout(
    init: (ConstraintLayout.(RelativeLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): ConstraintLayout {
    val view = ConstraintLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun LinearLayout.constraintLayout(
    init: (ConstraintLayout.(LinearLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): ConstraintLayout {
    val view = ConstraintLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun FrameLayout.constraintLayout(
    init: (ConstraintLayout.(FrameLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): ConstraintLayout {
    val view = ConstraintLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun ConstraintLayout.constraintLayout(
    init: (ConstraintLayout.(ConstraintLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): ConstraintLayout {
    val view = ConstraintLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CoordinatorLayout.constraintLayout(
    init: (ConstraintLayout.(CoordinatorLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): ConstraintLayout {
    val view = ConstraintLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun CollapsingToolbarLayout.constraintLayout(
    init: (ConstraintLayout.(CollapsingToolbarLayout.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): ConstraintLayout {
    val view = ConstraintLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

fun RecyclerView.constraintLayout(
    init: (ConstraintLayout.(RecyclerView.LayoutParams) -> Unit)? = null,
    width: Int = Wrap,
    height: Int = Wrap
): ConstraintLayout {
    val view = ConstraintLayout(this.context)
    val params = params(width, height)
    view.layoutParams = params
    init?.let { view.it(params) }
    addView(view)
    return view
}

/************Properties**************/

fun ConstraintLayout.LayoutParams.chainsColumnTopBottom(viewTopId: Int = -1) {
    if (viewTopId == -1) {
        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        topToBottom = viewTopId
    }
    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
}

fun ConstraintLayout.LayoutParams.chainsColumnCenter(viewTopId: Int = -1, viewBottomId: Int = -1) {
    if (viewTopId == -1) {
        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        topToBottom = viewTopId
    }
    if (viewBottomId == -1) {
        bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        bottomToTop = viewBottomId
    }
    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
}


fun ConstraintLayout.LayoutParams.chainsColumnBottomTop(viewBottomId: Int = -1) {
    if (viewBottomId == -1) {
        bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        bottomToTop = viewBottomId
    }
    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
}

fun ConstraintLayout.LayoutParams.chainsRowBottomStart(viewStartId: Int) {
    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
    endToStart = viewStartId
    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
}

fun ConstraintLayout.LayoutParams.chainsRowBottomCenter(viewStartId: Int, viewEndId: Int) {
    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
    endToStart = viewStartId
    startToEnd = viewEndId
}

fun ConstraintLayout.LayoutParams.chainsRowBottomEnd(viewEndId: Int) {
    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
    startToEnd = viewEndId
}

fun ConstraintLayout.LayoutParams.center() {
    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
}

fun ConstraintLayout.LayoutParams.centerHorizontal() {
    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
}

fun ConstraintLayout.LayoutParams.centerVertical() {
    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
}

fun ConstraintLayout.LayoutParams.centerTop(viewTopId: Int = -1) {
    if (viewTopId == -1) {
        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        topToBottom = viewTopId
    }
    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
}

fun ConstraintLayout.LayoutParams.centerBottom(viewBottomId: Int = -1) {
    if (viewBottomId == -1) {
        bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        bottomToTop = viewBottomId
    }
    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
}

fun ConstraintLayout.LayoutParams.centerStart(viewStartId: Int = -1) {
    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
    if (viewStartId == -1) {
        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        startToEnd = viewStartId
    }
}

fun ConstraintLayout.LayoutParams.centerEnd(viewEndId: Int = -1) {
    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
    if (viewEndId == -1) {
        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        endToStart = viewEndId
    }
}

fun ConstraintLayout.LayoutParams.centerColumn(viewTopId: Int = -1, viewBottomId: Int = -1) {
    if (viewTopId == -1) {
        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        topToBottom = viewTopId
    }
    if (viewBottomId == -1) {
        bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        bottomToTop = viewBottomId
    }
    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
}

fun ConstraintLayout.LayoutParams.centerRow(viewStartId: Int = -1, viewEndId: Int = -1) {
    if (viewStartId == -1) {
        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        startToEnd = viewStartId
    }
    if (viewEndId == -1) {
        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        endToStart = viewEndId
    }
    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
}

fun ConstraintLayout.LayoutParams.topStart(viewTopId: Int = -1, viewStartId: Int = -1) {
    if (viewTopId == -1) {
        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        topToBottom = viewTopId
    }
    if (viewStartId == -1) {
        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        startToEnd = viewStartId
    }
}

fun ConstraintLayout.LayoutParams.topEnd(viewTopId: Int = -1, viewStartId: Int = -1) {
    if (viewTopId == -1) {
        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        topToBottom = viewTopId
    }
    if (viewStartId == -1) {
        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        endToStart = viewStartId
    }
}

fun ConstraintLayout.LayoutParams.bottomStart(viewBottomId: Int = -1, viewStartId: Int = -1) {
    if (viewBottomId == -1) {
        bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        bottomToTop = viewBottomId
    }
    if (viewStartId == -1) {
        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        startToEnd = viewStartId
    }
}

fun ConstraintLayout.LayoutParams.bottomEnd(viewTopId: Int = -1, viewEndId: Int = -1) {
    if (viewTopId == -1) {
        bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        bottomToTop = viewTopId
    }
    if (viewEndId == -1) {
        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        endToStart = viewEndId
    }
}

fun ConstraintLayout.LayoutParams.topToBottomOf(viewTopId: Int) {
    topToBottom = viewTopId
}

fun ConstraintLayout.LayoutParams.bottomToTopOf(viewBottomId: Int) {
    bottomToTop = viewBottomId
}

fun ConstraintLayout.LayoutParams.endToStartOf(viewEndId: Int) {
    endToStart = viewEndId
}

fun ConstraintLayout.LayoutParams.startToEndOf(viewStartId: Int) {
    startToEnd = viewStartId
}

fun ConstraintLayout.LayoutParams.sameRowStartToEndOf(viewStartId: Int, viewEndId: Int = -1) {
    topToTop = viewStartId
    bottomToBottom = viewStartId
    startToEnd = viewStartId

    endToEnd = if (viewEndId == -1) {
        ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        viewEndId
    }
}

fun ConstraintLayout.LayoutParams.sameRowEndToStartOf(viewStartId: Int, viewEndId: Int = -1) {
    topToTop = viewEndId
    bottomToBottom = viewEndId
    endToStart = viewEndId
    startToStart = if (viewStartId == -1) {
        ConstraintLayout.LayoutParams.PARENT_ID
    } else {
        viewStartId
    }
}
