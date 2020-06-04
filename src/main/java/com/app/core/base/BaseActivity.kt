package com.app.core.base

import android.arch.lifecycle.LifecycleObserver
import android.os.Bundle
import android.os.Handler
import android.support.annotation.CallSuper
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.app.core.R
import com.app.core.action.Action
import com.app.core.action.Action1
import com.app.core.action.Action2
import com.app.core.constant.DefaultConstants
import com.app.core.log.Logger
import com.app.core.log.Trace
import com.app.core.rx.Bus
import com.app.core.thread.UIThread
import com.app.core.ui.animation.UIAnimation
import com.app.core.ui.image.Config
import com.app.core.ui.layout.UIFrameLayout
import com.app.core.ui.layout.UILayout
import com.app.core.ui.layout.UIRelativeLayout
import com.app.core.ui.view.UIContext
import com.app.core.ui.view.UIView
import com.app.core.util.NetUtil
import io.reactivex.functions.Consumer
import java.lang.ref.WeakReference

abstract class BaseActivity<T : BaseActivityViewModel> : AppCompatActivity(), BasePermission, FragmentManager.OnBackStackChangedListener {
    var isMoving = false
    var isRedirectPage = false
    private var mHandler: Handler? = null
    private var main: WeakReference<RelativeLayout>? = null
    private var content: WeakReference<ViewGroup>? = null
    private var viewModel: T? = null
    private var swipeListener: BaseSwipeActListener? = null

    open fun handleUncaughtException(thread: Thread, ex: Throwable) {
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(10)
        //UIUtil.sendMail(this, "", "error", e.getMessage());
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Trace.enable()) {
            Thread.setDefaultUncaughtExceptionHandler { thread, ex -> this.handleUncaughtException(thread, ex) }
        }
        setContentView(UIRelativeLayout.create(this) { it0 ->
            main = WeakReference(it0)
            it0.id = R.id.main
            // UIView.paddingTop(it0, UIContext.statusBarHeight(this));
            UIFrameLayout.add(it0, { it1, _ ->
                content = WeakReference(it1)
                it1.id = R.id.content
                UIView.backgroundTransparent(it1)
            }, UILayout.Match, UILayout.Match)
        }, UIContext.match())

        mHandler = Handler()

        viewModel = createViewModel()
        controller().init(this)
        swipeListener = BaseSwipeActListener(this)

        viewModel?.addListenNetwork(this, Action2 { isConnected, isReload ->
            networkAvailable(isConnected)
            if (isReload) reloadNetworkAvailable()
        })
        viewModel?.addListenKeyboard(main?.get() as RelativeLayout)

        supportFragmentManager.addOnBackStackChangedListener(this)

        uiChangeListener()

        if (savedInstanceState == null) {
            loadFragment()
        }
        loadView(savedInstanceState)
        UIThread.run({ if (viewModel?.tabIndex != -1) controller().tab(viewModel!!.tabIndex) }, UIAnimation.animWaitTime)
    }

    open fun controller(): BaseNavigation {
        return Navigation.instance
    }

    open fun loadFragment() {}

    @CallSuper
    open fun loadView(savedInstanceState: Bundle?) {
        Bus.normal()?.subscribe<Boolean>(DefaultConstants.ActionListener.KEYBOARD_CLOSE, Consumer {
            this.hideSoftKeyboard(currentFocus)
            currentFocus?.clearFocus()
        })
        Bus.normal()?.subscribe<Boolean>(DefaultConstants.ActionListener.KEYBOARD_CLOSE_NONE_CLEAR, Consumer {
            this.hideSoftKeyboard(currentFocus)
        })
        Bus.normal()?.subscribe<Boolean>(DefaultConstants.ActionListener.LOADING, Consumer { isLoading ->
            if (isLoading == true) this.loading() else this.unloading()
        })
        Logger.w("Load View Activity : " + this.javaClass.name)
    }

    @CallSuper
    open fun unLoadView() {
        Bus.normal()?.unSubscribeAll()
        Bus.navigation()?.unSubscribeAll()
        Bus.behavior()?.unSubscribeAll()
        Bus.release()
        Logger.w("unLoad View Activity : " + this.javaClass.name)
    }

    abstract fun createViewModel(): T

    open fun loading() {}

    open fun unloading() {}

    open fun reloadNetworkAvailable() {}

    open fun networkAvailable(isAvailable: Boolean?) {
        NetUtil.connected = isAvailable
    }

    open fun updateBackStack() {}

    override fun onDestroy() {
        super.onDestroy()
        content = null
        main = null
        mHandler?.removeCallbacksAndMessages(null)
        supportFragmentManager.removeOnBackStackChangedListener(this)
        unLoadView()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Config.clearCache()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        return if (swipeListener != null) {
            swipeListener!!.dispatchTouchEvent(event) || super.dispatchTouchEvent(event)
        } else super.dispatchTouchEvent(event)
    }


    fun subscribe(observer: LifecycleObserver) {
        lifecycle.addObserver(observer)
    }

    fun main(): RelativeLayout? {
        return main?.get()
    }

    fun content(): ViewGroup? {
        return content?.get()
    }

    fun viewModel(): T? {
        return viewModel
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (grantResults.isNotEmpty()) {
            when (requestCode) {
                DefaultConstants.RequestCode.LOCATION, DefaultConstants.RequestCode.LOCATION_UPDATE -> if (isGranted(grantResults[0])) {
                    viewModel?.requestLocation(requestCode == DefaultConstants.RequestCode.LOCATION_UPDATE)
                }
                else -> {
                }
            }
        }

        viewModel?.getPermission()?.postValue(PermissionData(requestCode, if (!permissions.isNullOrEmpty()) permissions else arrayOf(""), if (grantResults.isNotEmpty()) grantResults else intArrayOf(-1)))
    }

    override fun onBackStackChanged() {
        updateBackStack()
        currentFragment()?.updateBackStack()
        Bus.normal()?.publish(DefaultConstants.ActionListener.KEYBOARD_CLOSE, true)
        isMoving = false
    }

    override fun onBackPressed() {
        if (!isMoving) {
            Bus.normal()?.publish(DefaultConstants.ActionListener.POP_FRAGMENT, true)
            isMoving = true
            UIThread.run({ isMoving = false }, UIAnimation.animTime)
            val currentFragment = currentFragment()
            if (currentFragment != null) {
                val frag = WeakReference(currentFragment)
                frag.get()?.run {
                    this.onBackPressed(Action {
                        if (supportFragmentManager.backStackEntryCount <= 1) {
                            backHomeScreen()
                        } else {
                            if (this.typeNavigation != AnimatedNavigation.Replace) {
                                removeEmptyFragment(Action { super@BaseActivity.onBackPressed() })
                            } else {
                                super@BaseActivity.onBackPressed()
                            }
                        }
                    }, false)
                } ?: run {
                    super.onBackPressed()
                }
            } else {
                super.onBackPressed()
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        setImmersiveMode()
    }


    @CallSuper
    open fun uiChangeListener() {
        val decorView = window.decorView
        decorView.setOnSystemUiVisibilityChangeListener { visibility: Int ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                setImmersiveMode()
            }
        }
    }

    fun getHandler(action1: Action1<Handler>?) {
        if (mHandler != null) {
            action1?.invoke(mHandler)
        }
    }

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
