package com.app.core.base

import android.os.Bundle
import android.os.Handler
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.app.core.R
import com.app.core.action.Action
import com.app.core.action.Action1
import com.app.core.log.Logger
import com.app.core.thread.UIThread
import com.app.core.ui.animation.UIAnimation
import com.app.core.ui.layout.UIRelativeLayout
import com.app.core.ui.view.UIContext
import com.app.core.ui.view.UIView
import io.reactivex.disposables.CompositeDisposable
import java.util.*

abstract class UIViewController<T : UIViewControllerViewModel> : Fragment(), FragmentManager.OnBackStackChangedListener {
    val handler = Handler()
    var viewModel: T? = null
        private set
    var typeNavigation: AnimatedNavigation? = null
    private val data = ArrayList<Any>()
    val disposables: CompositeDisposable? = CompositeDisposable()

    operator fun get(action: Action1<BaseActivity<*>>) {
        if (activity != null && activity is BaseActivity<*>) {
            action.invoke(activity as BaseActivity<*>?)
        }
    }

    fun addData(o: Any) {
        data.add(o)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        childFragmentManager.removeOnBackStackChangedListener(this)
        unLoadView()
        viewModel?.dispose()
        Logger.w("destroy : " + this.javaClass.name)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = createViewModel()
        typeNavigation?.let {
            viewModel?.typeNavigation = it
            viewModel?.let {
                setViewModel(it, data, savedInstanceState)
            }
        }
        if (parentFragment == null)
            childFragmentManager.addOnBackStackChangedListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Logger.w("onCreateView: " + this.javaClass.name)
        context?.let { ctx ->
            super.onCreateView(inflater, container, savedInstanceState)
            return UIRelativeLayout.create(ctx) {
                it.id = R.id.contentFrag
                it.layoutParams = UIContext.match()
                it.isClickable = true
                it.isFocusable = true
                it.visibility = View.INVISIBLE
                UIView.backgroundColor(it, R.color.c1)
                loadBackground(it, inflater)
                when (viewModel?.typeNavigation) {
                    AnimatedNavigation.Present, AnimatedNavigation.Swipe -> {
                        loadView(it, savedInstanceState, inflater)
                        didView(it)
                        if (typeNavigation != null) {
                            didFragment(it)
                        }
                    }
                    else -> {
                        loadView(it, savedInstanceState, inflater)
                        didView(it)
                        if (typeNavigation != null) {
                            didFragment(it)
                        }
                    }
                }
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.w("onViewCreated: " + this.javaClass.name)
        view.visibility = View.VISIBLE
        if (typeNavigation != null) {
            val previousView = realManager(isOnCreate = true)?.previousView()
            when (viewModel?.typeNavigation) {
                AnimatedNavigation.Present -> {
                    previousView?.startAnimation(UIAnimation.removePresent())
                    view.startAnimation(UIAnimation.addPresent())
                }
                AnimatedNavigation.Swipe -> {
                    previousView?.startAnimation(UIAnimation.removeSwipe())
                    view.startAnimation(UIAnimation.addSwipe())
                }
                else -> {
                }
            }
        }
    }

    open fun onBackPressed(result: Action, isRoot: Boolean) {
        UIThread.run {
            val previousView = realManager(isRoot)?.previousView()
            val cu = realManager(isRoot)?.currentChildFragment()
            Logger.w("onBackPressed " + this.javaClass.name)
            cu?.let {
                if (it.viewModel != null && it.view != null) {
                    when (it.viewModel?.typeNavigation) {
                        AnimatedNavigation.Present, AnimatedNavigation.Swipe -> {
                            pop(it.view!!, previousView, result, it.viewModel!!.typeNavigation)
                            return@run
                        }
                        else -> {
                        }
                    }
                }
            }
            if (!isRoot && isChildManager(false)) childFragmentManager.popBackStack() else result.invoke()
        }
    }

    override fun onBackStackChanged() {
        val manager = rootManager()
        if (manager != null && manager.backStackEntryCount > 0) {
            val baseFragment = manager.findFragmentById(R.id.contentFrag)
            if (baseFragment is UIViewController<*>) {
                baseFragment.updateChildBackStack()
                UIThread.run({ get(Action1 { a -> a.isMoving = false }) }, UIAnimation.animLongTime)
            }
        }
    }

    open fun updateChildBackStack() {}

    open fun updateBackStack() {}

    open fun setViewModel(viewModel: T, data: List<Any>, savedInstanceState: Bundle?) {}

    abstract fun createViewModel(): T
    open fun didView(main: RelativeLayout) {}

    open fun didFragment(main: RelativeLayout) {}

    open fun loadBackground(main: RelativeLayout, inflater: LayoutInflater) {}

    open fun loadView(main: RelativeLayout, savedInstanceState: Bundle? = null, inflater: LayoutInflater) {}

    @CallSuper
    open fun unLoadView() {
        disposables?.clear()
        System.gc()
    }
}
