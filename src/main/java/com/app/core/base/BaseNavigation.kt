package com.app.core.base

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.annotation.MainThread
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentManager
import android.support.v4.content.PermissionChecker
import com.app.core.action.Action
import com.app.core.action.Action1
import com.app.core.constant.DefaultConstants
import com.app.core.rx.Bus
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.lang.ref.WeakReference
import kotlin.system.exitProcess

abstract class BaseNavigation {
    private val PERMISSION_REQUEST_CODE: Int = 1
    private var _activity: WeakReference<BaseActivity<*>>? = null

    fun init(activity: BaseActivity<*>) {
        _activity = WeakReference(activity)
    }

    operator fun get(action: (a: BaseActivity<*>) -> Unit) {
        (_activity?.get()?.let {
            action.invoke(it)
        })
    }

    @MainThread
    fun requestLocation(isFrequencyUpdate: Boolean?) {
        get { a -> ActivityCompat.requestPermissions(a, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), if (isFrequencyUpdate == true) DefaultConstants.RequestCode.LOCATION_UPDATE else DefaultConstants.RequestCode.LOCATION) }
    }

    @MainThread
    fun initTab(vararg tabs: UIViewController<*>) {
        if (tabs.isNotEmpty()) {
            Bus.normal()?.subscribe<Int>(DefaultConstants.TabChange.MESSAGE_KEY, Consumer {
                it?.let { index ->
                    if (tabs.size > index && index >= 0) {
                        addTab(tabs[index], index)
                    }
                }
            })
        }
    }

    fun tabListener(action: Action1<Int>): Disposable? {
        return Bus.normal()?.subscribe<Int>(DefaultConstants.TabChange.MESSAGE_KEY, Consumer { action.invoke(it) })
    }

    fun tab(index: Int) {
        Bus.normal()?.publish(DefaultConstants.TabChange.MESSAGE_KEY, if (index < 0) -1 else index)
    }

    fun addTab(newFragment: UIViewController<*>, index: Int) {
        get { a -> a.tabChange(newFragment, index) }
    }

    private fun gotoSettings(context: Context) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }
    @MainThread
    fun requestPermission(
        action: () -> Unit,
        requestPermission: String,
        refuseAction: () -> Unit,
        requestCode: Int = PERMISSION_REQUEST_CODE
    ) {
        get {
            if (PermissionChecker.checkSelfPermission(it, requestPermission) == PermissionChecker.PERMISSION_GRANTED) {
                action()
            } else {
                ActivityCompat.requestPermissions(it, arrayOf(requestPermission), requestCode)
            }
            permissionListener { code, permission, grantResult ->
                if (code == requestCode && permission[0] == requestPermission && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    action()
                } else {
                    refuseAction()
                }
            }
        }
    }
    @MainThread
    fun requestMultiplePermission(
        action: () -> Unit,
        requestPermission: Array<String>,
        refuseAction: () -> Unit,
        requestCode: Int = PERMISSION_REQUEST_CODE
    ) {
        get {
            ActivityCompat.requestPermissions(it, requestPermission, requestCode)
            permissionListener { code, permission, grantResult ->
                var allAreGranted = true
                grantResult.forEach {
                    if (it != PackageManager.PERMISSION_GRANTED) {
                        allAreGranted = false
                        return@forEach
                    }
                }
                if (code == requestCode && permission.contentEquals(requestPermission) && allAreGranted) {
                    action()
                } else {
                    refuseAction()
                }
            }
        }
    }

    fun permissionListener(init: BaseActivity<*>.(requestCode: Int, permission: Array<String>, grantResult: IntArray) -> Unit) {
        get {
            it.viewModel()?.addListenPermission(it, Action1 { permissionData ->
                permissionData?.run {
                    it.init(requestCode, permission, grantResult)
                }
            })
        }
    }

    fun keyboardListener(action: Action1<Int>): Disposable? {
        return Bus.normal()?.subscribe<Int>(DefaultConstants.ActionListener.KEYBOARD, Consumer { action.invoke(it) })
    }

    fun pushViewController(viewController: UIViewController<*>, isRoot: Boolean = false, animated: AnimatedNavigation = AnimatedNavigation.None, isDuplicated: Boolean = false, isInTab: Boolean = false, isAnimatedFromRight: Boolean = true) {
        get { a ->
            a.pushViewController(viewController, isRoot, animated, isDuplicated, isInTab, isAnimatedFromRight)
        }
    }

    fun closeApp() {
        get { it.finish() }
        exitProcess(2)
    }

    fun removeToRoot() {
        get { it.removeToRoot() }
    }

    fun removeFragment(tagName: String) {
        get { a -> a.removeFragmentOutOfBackStack(tagName) }
    }

    fun pop() {
        get(BaseActivity<*>::onBackPressed)
    }

    fun popToTabRoot() {
        get { a ->
            val currentFragment = a.currentFragment()
            if (currentFragment?.childFragmentManager?.backStackEntryCount ?: 0 > 1) {
                val index = 0
                val tagName = currentFragment?.childFragmentManager?.getBackStackEntryAt(index)?.name
                Bus.normal()?.publish(DefaultConstants.ActionListener.POP_FRAGMENT, true)
                currentFragment?.childFragmentManager?.popBackStackImmediate(tagName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }
    }

    fun popTabRoot() {
        get { a ->
            Bus.normal()?.publish(DefaultConstants.ActionListener.POP_FRAGMENT, true)
            val backStackEntryCount = a.supportFragmentManager.backStackEntryCount
            val currentFragment = a.currentFragment()
            if (currentFragment != null) {
                val frag = WeakReference<UIViewController<*>>(currentFragment)
                frag.get()?.run {
                    this.onBackPressed(Action{
                        if (backStackEntryCount <= 1) {
                            a.backHomeScreen()
                        } else {
                            a.supportFragmentManager.popBackStack()
                        }
                    }, true)
                } ?: run {
                    a.supportFragmentManager.popBackStack()
                }
            } else {
                a.supportFragmentManager.popBackStack()
            }
        }
    }

    fun popToRoot() {
        get { a ->
            if (a.supportFragmentManager.backStackEntryCount > 1) {
                val index = 1
                val tagName = a.supportFragmentManager.getBackStackEntryAt(index).name
                Bus.normal()?.publish(DefaultConstants.ActionListener.POP_FRAGMENT, true)
                a.supportFragmentManager.popBackStackImmediate(tagName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }
    }
}
