package com.app.core.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.app.core.R
import com.app.core.ui.layout.center
import com.app.core.ui.layout.ui

@SuppressLint("ViewConstructor")
class UICircleIndicator(context: Context, @ColorRes rColorSelected: Int, @ColorRes rColorUnSelected: Int, @DimenRes rDimenW: Int, @DimenRes rDimenH: Int, @DimenRes rDimenMargin: Int) :
    LinearLayout(context) {
    private var mViewpager: ViewPager? = null
    private var mIndicatorMargin = -1
    private var mIndicatorWidth = -1
    private var mIndicatorHeight = -1
    private var mIndicator: Drawable? = null
    private var mIndicatorUnselected: Drawable? = null
    private var mLastPosition = -1
    private val mInternalPageChangeListener = object : OnPageChangeListener {

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int) {
            if (mViewpager?.adapter == null || mViewpager?.adapter!!.count <= 0) {
                return
            }
            var index = position
            if (mViewpager?.adapter is LoopingPagerAdapter) {
                index = position % (mViewpager!!.adapter as LoopingPagerAdapter).realCount
            }
            val currentIndicator: View? = getChildAt(mLastPosition)
            if (mLastPosition >= 0 && currentIndicator != null) {
                (currentIndicator.findViewById(R.id.btn) as? View)?.background(mIndicatorUnselected)
            }

            val selectedIndicator = getChildAt(index)
            if (selectedIndicator != null) {
                (selectedIndicator.findViewById(R.id.btn) as? View)?.background(mIndicator)
            }
            mLastPosition = index
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

    init {
        init(context, rColorSelected, rColorUnSelected, rDimenW, rDimenH, rDimenMargin)

    }

    private fun init(context: Context, @ColorRes rColorSelected: Int, @ColorRes rColorUnSelected: Int, @DimenRes rDimenW: Int, @DimenRes rDimenH: Int, @DimenRes rDimenMargin: Int) {
        mIndicator = context.circle(rColorSelected)
        mIndicatorUnselected = context.circle(rColorUnSelected)
        mIndicatorWidth = context.dimen(rDimenW)
        mIndicatorHeight = context.dimen(rDimenH)
        mIndicatorMargin = context.dimen(rDimenMargin)
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER
    }

    fun setViewPager(viewPager: ViewPager) {
        mViewpager = viewPager
        if (mViewpager!!.adapter != null) {
            mLastPosition = -1
            createIndicators()
            if (mViewpager!!.adapter is LoopingPagerAdapter) {
                mViewpager!!.currentItem = (mViewpager!!.adapter as LoopingPagerAdapter).realCount * 5000
            }
            mViewpager!!.removeOnPageChangeListener(mInternalPageChangeListener)
            mViewpager!!.addOnPageChangeListener(mInternalPageChangeListener)
            mInternalPageChangeListener.onPageSelected(mViewpager!!.currentItem)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun createIndicators() {
        removeAllViews()
        if (mViewpager!!.adapter != null) {
            var count = mViewpager!!.adapter!!.count
            if (mViewpager!!.adapter is LoopingPagerAdapter) {
                count = (mViewpager!!.adapter as LoopingPagerAdapter).realCount
            }
            if (count <= 0) {
                return
            }
            val currentItem = mViewpager!!.currentItem
            val orientation = orientation

            for (i in 0 until count) {
                val indicator: View
                if (currentItem == i) {
                    indicator = addIndicator(orientation, mIndicator!!)
                } else {
                    indicator = addIndicator(orientation, mIndicatorUnselected!!)
                }
                indicator.setOnTouchListener { _, _ ->
                    if (mViewpager!!.adapter is LoopingPagerAdapter) {
                        var position = mViewpager!!.currentItem
                        val index = position % (mViewpager!!.adapter as LoopingPagerAdapter).realCount
                        position = position + i - index
                        mViewpager!!.setCurrentItem(position, true)
                    } else {
                        mViewpager!!.setCurrentItem(i, true)
                    }
                    true
                }
            }
        }
    }
    @SuppressWarnings("unchecked")
    private fun addIndicator(@Suppress("UNUSED_PARAMETER")orientation: Int, drawable: Drawable): View {
        val indicator = RelativeLayout(context)
        indicator.isClickable = true
        indicator.isFocusable = true

        indicator.ui(View(context), {
            id = R.id.btn
            center()
            background(drawable)
        }, mIndicatorWidth, mIndicatorHeight)
        addView(indicator, mIndicatorWidth + 2 * mIndicatorMargin, mIndicatorHeight + 2 * mIndicatorMargin)
        val lp = indicator.layoutParams as LinearLayout.LayoutParams
//        if (orientation == HORIZONTAL) {
//            lp.leftMargin = mIndicatorMargin;
//            lp.rightMargin = mIndicatorMargin;
//        } else {
//            lp.topMargin = mIndicatorMargin;
//            lp.bottomMargin = mIndicatorMargin;
//        }
        indicator.padding(mIndicatorMargin)
        indicator.layoutParams = lp
        return indicator
    }
}
