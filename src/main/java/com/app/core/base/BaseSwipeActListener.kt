package com.app.core.base

import android.animation.Animator
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.app.core.action.Action
import com.app.core.thread.UIThread
import com.app.core.ui.animation.UIAnimation
import com.app.core.ui.view.statusBarHeight
import com.app.core.ui.view.width
import java.lang.ref.WeakReference

internal class BaseSwipeActListener(activity: BaseActivity<*>) {
    private var downX: Float = 0.toFloat()
    private var upX: Float = 0.toFloat()
    private var downY: Float = 0.toFloat()
    private val mEdgeSlop: Float
    private val width: Int
    private val animTime: Long
    private val activity: WeakReference<BaseActivity<*>?>?
    private val navHeight: Int

    init {
        this.activity = WeakReference(activity)
        val config = ViewConfiguration.get(activity)
        mEdgeSlop = (config.scaledEdgeSlop + 20).toFloat()
        width = activity.width()
        animTime = UIAnimation.animTime
        downX = 0f
        upX = 0f
        downY = 0f
        navHeight = activity.statusBarHeight()
    }

    fun dispatchTouchEvent(event: MotionEvent): Boolean {
        activity?.get()?.let {
            if (!it.isRedirectPage) {
                val pointerIndex = event.actionIndex
                val pointerId = event.getPointerId(pointerIndex)
                val maskedAction = event.actionMasked
                if (pointerId == 0) {
                    when (maskedAction) {
                        MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                            downX = event.rawX
                            upX = event.rawX
                            downY = event.rawY
                        }
                    }
                }
                val cuFragment = it.currentFragment()
                if (pointerId == 0 && cuFragment != null && downY >= navHeight && downX <= mEdgeSlop) {
                    var curView: WeakReference<View?>? = null
                    var backView: WeakReference<View?>? = null
                    val manager = cuFragment.realManager(false)
                    val previousView = manager?.previousView()
                    val cu = manager?.currentChildFragment()
                    if (cu?.viewModel != null && cu.view != null && previousView != null) {
                        when (cu.viewModel!!.typeNavigation) {
                            AnimatedNavigation.Swipe -> {
                                curView = WeakReference(cu.view)
                                backView = WeakReference(previousView)
                            }
                            else -> {
                            }
                        }
                    }

                    if (curView?.get() != null && backView?.get() != null) {
                        when (maskedAction) {
                            MotionEvent.ACTION_MOVE -> {
                                upX = event.rawX
                                if (upX - downX >= 0) {
                                    if (upX - downX < 10) {
                                        backView.get()?.translationX = -width.toFloat() / 3
                                    }
                                    backView.get()?.animate()?.translationX((upX - downX) / 3 - width.toFloat() / 3)
                                        ?.duration = 0
                                    curView.get()?.animate()?.translationX(upX - downX)?.duration = 0
                                }
                                it.isMoving = true
                            }
                            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> {
                                if (upX - downX > width / 2) {
                                    var time = animTime.toFloat()
                                    val x: Float = curView.get()?.x ?: 0f;
                                    time *= (width.toFloat() - x) / width
                                    if (time > 0) {
                                        backView.get()?.animate()?.translationX(0f)?.duration = time.toLong()
                                        curView.get()?.animate()?.translationX((width + 10).toFloat())
                                            ?.setDuration(time.toLong())
                                            ?.setListener(object : Animator.AnimatorListener {
                                                override fun onAnimationStart(animation: Animator) {}

                                                override fun onAnimationEnd(animation: Animator) {
                                                    it.removeEmptyFragment(Action { manager?.popBackStackImmediate() })
                                                }

                                                override fun onAnimationCancel(animation: Animator) {
                                                    it.removeEmptyFragment(Action { manager?.popBackStackImmediate() })
                                                }

                                                override fun onAnimationRepeat(animation: Animator) {

                                                }
                                            })
                                    } else {
                                        curView.get()?.animate()?.translationX(0f)?.duration = animTime / 3
                                        backView.get()?.animate()?.translationX(0f)?.duration = animTime / 3
                                    }
                                } else {
                                    curView.get()?.animate()?.translationX(0f)?.duration = animTime / 3
                                    backView.get()?.animate()?.translationX(0f)?.duration = animTime / 3
                                }
                                UIThread.run({
                                    it.isMoving = false
                                }, UIAnimation.animLongTime)
                                downX = width.toFloat()
                                upX = downX
                            }
                            else -> {
                            }
                        }
                    }
                }
            }
        }
        return false
    }
}
