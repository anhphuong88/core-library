package com.app.core.ui.layout

import android.content.Context
import android.support.v7.widget.GridLayoutManager

class UIGridLayoutManager(
    context: Context,
    column: Int,
    orientation: Int,
    reverseLayout: Boolean = false
) : GridLayoutManager(context, column, orientation, reverseLayout) {

    var isScrollVerticalEnable = true
    var isScrollHorizontalEnable = true

    override fun canScrollVertically(): Boolean {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollVerticalEnable && super.canScrollVertically()
    }

    override fun canScrollHorizontally(): Boolean {
        return isScrollHorizontalEnable && super.canScrollHorizontally()
    }
}