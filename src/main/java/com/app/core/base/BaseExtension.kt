package com.app.core.base

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import com.app.core.R
import com.app.core.action.Action
import com.app.core.action.Action1
import com.app.core.log.Logger
import com.app.core.thread.UIThread
import com.app.core.ui.animation.UIAnimation
import com.app.core.util.StringUtil
import java.util.ArrayList

enum class AnimatedNavigation {
    Swipe,
    Present,
    Replace,
    None
}

fun AppCompatActivity.removeFragmentOutOfBackStack(tagName: String) {
    if (supportFragmentManager != null) {
        for (fragment in supportFragmentManager.fragments) {
            if (fragment is UIViewController<*> && fragment.getTag() != null) {
                if (fragment.getTag().equals(tagName, ignoreCase = true)) {
                    supportFragmentManager.beginTransaction().remove(fragment).commitNow()
                    break
                }
            }
        }
    }
}

fun AppCompatActivity.removeToRoot() {
    var i = 0
    val tags = ArrayList<String>()
    while (supportFragmentManager != null && supportFragmentManager.backStackEntryCount > i + 2) {
        val index = supportFragmentManager.backStackEntryCount - 2 - i
        val tagName = supportFragmentManager.getBackStackEntryAt(index).name
        tagName?.run {tags.add(this)}
        i++
    }
    if (supportFragmentManager != null && tags.size > 0) {
        for (fragment in supportFragmentManager.fragments) {
            if (fragment is UIViewController<*> && fragment.getTag() != null) {
                if (tags.contains(fragment.getTag()!!)) {
                    supportFragmentManager.beginTransaction().remove(fragment).commitNow()
                }
            }
        }
    }
}

fun AppCompatActivity.setFullScreen(fullScreen: Boolean) {
    if (fullScreen) {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    } else {
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}

fun AppCompatActivity.setImmersiveMode() {
    //        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    //                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    //                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    //                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    setStatusBarColor()
}

fun AppCompatActivity.setStatusBarColor() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = ContextCompat.getColor(this, R.color.c3)
    }
}

fun AppCompatActivity.hideSoftKeyboard(view: View?) {
    view?.let {
        val input = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        if (input?.isActive == true) {
            input.hideSoftInputFromWindow(it.windowToken, 0)
        }
        setImmersiveMode()
    }
}

fun AppCompatActivity.backHomeScreen() {
    val startMain = Intent(Intent.ACTION_MAIN)
    startMain.addCategory(Intent.CATEGORY_HOME)
    startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(startMain)
}
/**
 * Check current fragment has child or not to get child manager
 * if onCreate is true, it will get child manager of parent fragment
 * if onCreate is false, it will require it's child manager has more than 2 entries count.
 * @param isRoot is tab root or add child for tab
 */
fun Fragment.isChildManager(onCreate: Boolean): Boolean {
    return if (onCreate) {
        parentFragment != null && parentFragment?.childFragmentManager?.backStackEntryCount ?: 0 > 1
    } else {
        childFragmentManager.backStackEntryCount > 1
    }
}

/**
 * Return root child fragment manager.
 * If fragment has parent fragment it will return child manager of parent fragment.
 * Else it will return it's child manager.
 */
fun Fragment.rootManager(): FragmentManager? = if (parentFragment != null) parentFragment?.childFragmentManager else childFragmentManager

/**
 * Get current manager of root fragment.
 * isOnCreate spends for starting fragment and false is for backing or removing
 * @param isRoot is tab root or add child for tab
 * @param isOnCreate if true, it will return root manger of current fragment. false is child fragment
 */
fun Fragment.realManager(isRoot: Boolean = false, isOnCreate: Boolean = false): FragmentManager? {
    //case: is need not root and not on create and is child manager
    if (!isRoot && !isOnCreate && isChildManager(false)) {
        return childFragmentManager
    //case: is need not root and on create and is root manager of fragment
    } else if (!isRoot && isOnCreate && isChildManager(true)) {
        return rootManager()
    } else if (activity != null) {
        return activity!!.supportFragmentManager
    }
    return null
}

internal fun UIViewController<*>.replace(newFragment: UIViewController<*>, isTabRoot: Boolean = false) {
    get(Action1 { a ->
        val manager = if (isTabRoot) childFragmentManager else rootManager()
        manager?.let {
            if (!it.isStateSaved && !a.isMoving) {
                a.isMoving = true
                newFragment.typeNavigation = AnimatedNavigation.None
                val ft = it.beginTransaction()
                ft.setCustomAnimations(0, 0, 0, 0)
                it.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                for (fragment in it.fragments) {
                    if (fragment is UIViewController<*>) {
                        it.beginTransaction().remove(fragment).commitNowAllowingStateLoss()
                    }
                }
                val count = it.backStackEntryCount
                val tag = newFragment.javaClass.name + count
                ft.replace(R.id.contentFrag, newFragment, tag).commit()
                ft.addToBackStack(tag)
                it.executePendingTransactions()
            }
        }
    })
}

internal fun UIViewController<*>.add(newFragment: UIViewController<*>, typeNavigation: AnimatedNavigation, isFromReplaceRight: Boolean = true, isTabRoot: Boolean = false) {
    get(Action1 { a ->
        val manager = if (isTabRoot) childFragmentManager else rootManager()
        manager?.let {
            if (!it.isStateSaved && !a.isMoving) {
                a.isMoving = true
                newFragment.typeNavigation = typeNavigation
                val count = it.backStackEntryCount
                Logger.w("add child entry count : $count")
                val tag = newFragment.javaClass.name
                it.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val ft = it.beginTransaction()
                when(typeNavigation) {
                    AnimatedNavigation.Present, AnimatedNavigation.Swipe -> {
                        a.isRedirectPage = true
                        UIThread.run({ a.isRedirectPage = false }, UIAnimation.animLongTime)
                    }
                    AnimatedNavigation.Replace -> {
                        if (isFromReplaceRight) ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.pop_in, R.anim.pop_out)
                        else ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.pop_in_left, R.anim.pop_out_left)
                    }
                    else -> {}
                }
                if (typeNavigation == AnimatedNavigation.Replace) {
                    ft.replace(R.id.contentFrag, newFragment, tag)
                } else {
                    ft.add(R.id.contentFrag, newFragment, tag)
                }
                ft.addToBackStack(tag)
                ft.commitAllowingStateLoss()
            }
        }
    })
}

internal fun UIViewController<*>.add(newFragment: UIViewController<*>) {
    add(newFragment, AnimatedNavigation.None)
}

internal fun UIViewController<*>.addSwipe(newFragment: UIViewController<*>) {
    add(newFragment, AnimatedNavigation.Swipe)
}

internal fun UIViewController<*>.addPresent(newFragment: UIViewController<*>) {
    add(newFragment, AnimatedNavigation.Present)
}

internal fun UIViewController<*>.addReplace(newFragment: UIViewController<*>, isFromReplaceRight: Boolean = true) {
    add(newFragment, AnimatedNavigation.Replace, isFromReplaceRight)
}

fun Fragment.isCurrentFragment(): Boolean {
    var frag: UIViewController<*>? = null
    if (activity != null && activity is BaseActivity<*>) {
        frag = (activity as BaseActivity<*>).currentFragment()
    }

    if (frag != null && frag.tag != null) {
        val tag = frag.tag!!.trim { it <= ' ' }
        return tag.equals(this.tag!!, ignoreCase = true)
    }
    return false
}

fun FragmentManager.currentChildFragment(): UIViewController<*>? {
    var frag: UIViewController<*>? = null
    if (backStackEntryCount > 1) {
        val index = backStackEntryCount - 1
        val tagName = getBackStackEntryAt(index).name
        val current = findFragmentByTag(tagName)
        if (current != null && current.view != null && current is UIViewController<*>) {
            frag = current
        }
    }
    return frag
}

fun Fragment.pop(currentView: View, previousView: View?, result: Action, typeNavigation: AnimatedNavigation) {
    if (context != null) {
        var animation: Animation? = null
        when (typeNavigation) {
            AnimatedNavigation.Present -> animation = UIAnimation.popPresentRemove()
            AnimatedNavigation.Swipe -> animation = UIAnimation.popSwipeRemove()
            else -> {
            }
        }
        animation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                if (isChildManager(false)) childFragmentManager.popBackStack() else result.invoke()
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        currentView.startAnimation(animation)
    } else {
        if (isChildManager(false)) {
            childFragmentManager.popBackStack()
        } else
            result.invoke()
    }
    previousView?.let {
        when (typeNavigation) {
            AnimatedNavigation.Present -> it.startAnimation(UIAnimation.popPresentAdd())
            AnimatedNavigation.Swipe -> it.startAnimation(UIAnimation.popSwipeAdd())
            else -> {
            }
        }
    }
}

fun FragmentManager.previousView(): View? {
    var previousView: View? = null
    var i = 0
    while (backStackEntryCount > i + 1) {
        val index = backStackEntryCount - 2 - i
        val tagName = getBackStackEntryAt(index).name
        val previous = findFragmentByTag(tagName)
        if (previous != null && previous.view != null) {
            previousView = previous.view
            break
        } else {
            i++
        }
    }
    return previousView
}

@Suppress("UNCHECKED_CAST")
fun BaseActivity<*>.currentFragment(): UIViewController<*>?  {
    if (supportFragmentManager != null) {
        var fragment: Fragment?
        if (viewModel()?.tabIndex != -1) {
            fragment = supportFragmentManager.findFragmentByTag(BaseTabFragment::class.java.name + viewModel()?.tabIndex)
            if (fragment is UIViewController<*>) return fragment
        }
        fragment = supportFragmentManager.findFragmentById(R.id.content)
        if (fragment is UIViewController<*>) return fragment
    }
    return null
}

fun BaseActivity<*>.isCurrentFragment(className: String) = currentFragment()?.javaClass?.name?.equals(className) == true


fun BaseActivity<*>.removeEmptyFragment(action: Action) {
    if (viewModel() != null && viewModel()?.tabIndex == -1) {
        var i = 0
        var previousTag = ""
        while (supportFragmentManager != null && supportFragmentManager.backStackEntryCount > i + 1) {
            val index = supportFragmentManager.backStackEntryCount - 2 - i
            val tagName = supportFragmentManager.getBackStackEntryAt(index).name
            val previous = supportFragmentManager.findFragmentByTag(tagName)
            if (previous != null && previous.view != null) {
                if (!StringUtil.emptyTrim(previousTag)) {
                    supportFragmentManager.popBackStackImmediate(previousTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                } else {
                    action.invoke()
                }
                break
            } else {
                previousTag = "" + tagName
                i++
            }
        }
    } else {
        action.invoke()
    }
}

internal fun BaseActivity<*>.replace(newFragment: UIViewController<*>) {
    if (supportFragmentManager != null && !supportFragmentManager.isStateSaved && !isMoving) {
        viewModel()?.tabIndex = -1
        isMoving = true
        newFragment.typeNavigation = AnimatedNavigation.None
        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(0, 0, 0, 0)
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val count = supportFragmentManager.backStackEntryCount
        val tag = newFragment.javaClass.name + count
        ft.replace(R.id.content, newFragment, tag).commit()
        ft.addToBackStack(tag)
        supportFragmentManager.executePendingTransactions()
    }
}

internal fun BaseActivity<*>.add(newFragment: UIViewController<*>, typeNavigation: AnimatedNavigation, isDuplicate: Boolean, isFromReplaceRight: Boolean = true) {
    if (supportFragmentManager != null && !supportFragmentManager.isStateSaved && !isMoving) {
        viewModel()?.tabIndex = -1
        isMoving = true
        newFragment.typeNavigation = typeNavigation
        val count = supportFragmentManager.backStackEntryCount
        Logger.w("add entry count : $count")
        var tag = newFragment.javaClass.name
        if (!isDuplicate) {
            supportFragmentManager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } else {
            tag = newFragment.javaClass.name + count
        }
        val ft = supportFragmentManager.beginTransaction()
        when(typeNavigation) {
            AnimatedNavigation.Present, AnimatedNavigation.Swipe -> {
                isRedirectPage = true
                UIThread.run({ isRedirectPage = false }, UIAnimation.animLongTime)
            }
            AnimatedNavigation.Replace -> {
                if (isFromReplaceRight) ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.pop_in, R.anim.pop_out)
                else ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.pop_in_left, R.anim.pop_out_left)
            }
            else -> {}
        }
        if (typeNavigation == AnimatedNavigation.Replace) {
            ft.replace(R.id.content, newFragment, tag)
        } else {
            ft.add(R.id.content, newFragment, tag)
        }
        ft.addToBackStack(tag)
        ft.commitAllowingStateLoss()
    }
}

internal fun BaseActivity<*>.tabChange(newTab: UIViewController<*>, tabIndex: Int) {
    if (supportFragmentManager != null && !supportFragmentManager.isStateSaved && !isMoving) {
        val tagName = BaseTabFragment::class.java.name + tabIndex
        val tag = supportFragmentManager.findFragmentByTag(tagName)
        val tabFragment: BaseTabFragment
        Logger.w("tab start entry count : " + supportFragmentManager.backStackEntryCount)
        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(0, 0, 0, 0)
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        Logger.w("tab end entry count : " + supportFragmentManager.backStackEntryCount)
        viewModel()?.tabIndex = tabIndex
        for (fragment in supportFragmentManager.fragments) {
            if (fragment is UIViewController<*> && fragment.getTag() != null) {
                if (!fragment.getTag()!!.equals(tagName, ignoreCase = true)) {
                    supportFragmentManager.beginTransaction().hide(fragment).commitNowAllowingStateLoss()
                }
            }
        }
        if (tag is BaseTabFragment) {
            tabFragment = tag
            ft.show(tabFragment).commit()
            supportFragmentManager.executePendingTransactions()
        } else {
            tabFragment = BaseTabFragment.create(newTab)
            tabFragment.typeNavigation = AnimatedNavigation.None
            ft.add(R.id.content, tabFragment, tagName).commit()
            supportFragmentManager.executePendingTransactions()
        }
    }
}

internal fun BaseActivity<*>.add(newFragment: UIViewController<*>) {
    add(newFragment, AnimatedNavigation.None, false)
}

internal fun BaseActivity<*>.addSwipe(newFragment: UIViewController<*>) {
    add(newFragment, AnimatedNavigation.Swipe, false)
}

internal fun BaseActivity<*>.addPresent(newFragment: UIViewController<*>) {
    add(newFragment, AnimatedNavigation.Present, false)
}

internal fun BaseActivity<*>.addReplace(newFragment: UIViewController<*>, isFromReplaceRight: Boolean = true) {
    add(newFragment, AnimatedNavigation.Replace, false, isFromReplaceRight)
}

internal fun BaseActivity<*>.addDup(newFragment: UIViewController<*>) {
    add(newFragment, AnimatedNavigation.None, true)
}

internal fun BaseActivity<*>.addDupSwipe(newFragment: UIViewController<*>) {
    add(newFragment, AnimatedNavigation.Swipe, true)
}

internal fun BaseActivity<*>.addDupPresent(newFragment: UIViewController<*>) {
    add(newFragment, AnimatedNavigation.Present, true)
}

internal fun BaseActivity<*>.addDupReplace(newFragment: UIViewController<*>, isFromReplaceRight: Boolean = true) {
    add(newFragment, AnimatedNavigation.Replace, true, isFromReplaceRight)
}

fun UIViewController<*>.pushViewController(viewController: UIViewController<*>, isRoot: Boolean = false, animated: AnimatedNavigation = AnimatedNavigation.None, isAnimatedFromRight: Boolean = true) {
    if (isRoot) {
        replace(viewController, isTabRoot = true)
    } else {
        when(animated) {
            AnimatedNavigation.Present -> {
                add(viewController, typeNavigation = AnimatedNavigation.Present, isTabRoot = true)
            }
            AnimatedNavigation.Swipe -> {
                add(viewController, typeNavigation = AnimatedNavigation.Swipe, isTabRoot = true)
            }
            AnimatedNavigation.Replace -> {
                add(viewController, typeNavigation = AnimatedNavigation.Replace, isFromReplaceRight = isAnimatedFromRight,isTabRoot = true)
            }
            else -> {
                add(viewController, typeNavigation = AnimatedNavigation.None, isTabRoot = true)
            }
        }
    }
}

internal fun BaseActivity<*>.pushViewController(viewController: UIViewController<*>, isRoot: Boolean = false, animated: AnimatedNavigation = AnimatedNavigation.None, isDuplicated: Boolean = false, isInTab: Boolean = false, isAnimatedFromRight: Boolean = true) {
    if (isRoot) {
        if (isInTab) currentFragment()?.replace(viewController) else replace(viewController)
    } else {
        when(animated) {
            AnimatedNavigation.Present -> {
                if (isInTab) {
                    currentFragment()?.addPresent(viewController)
                } else {
                    if (isDuplicated) addDupPresent(viewController) else addPresent(viewController)
                }
            }
            AnimatedNavigation.Swipe -> {
                if (isInTab) {
                    currentFragment()?.addSwipe(viewController)
                } else {
                    if (isDuplicated) addDupSwipe(viewController) else addSwipe(viewController)
                }
            }
            AnimatedNavigation.Replace -> {
                if (isInTab) {
                    currentFragment()?.addReplace(viewController, isAnimatedFromRight)
                } else {
                    if (isDuplicated) addDupReplace(viewController) else addReplace(viewController, isAnimatedFromRight)
                }
            }
            else -> {
                if (isInTab) {
                    currentFragment()?.add(viewController)
                } else {
                    if (isDuplicated) addDup(viewController) else add(viewController)
                }
            }
        }
    }
}

internal fun FragmentManager.pushTabViewController(tabIndex: Int, newTab: UIViewController<*>?= null) {
    if (isStateSaved) {
        val tagName = "newTab_$tabIndex"
        val tag = findFragmentByTag(tagName)
        Logger.w("tab start entry count : $backStackEntryCount")
        val ft = beginTransaction()
        ft.setCustomAnimations(0, 0, 0, 0)
        popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        Logger.w("tab end entry count : $backStackEntryCount")
        for (fragment in fragments) {
            if (fragment is UIViewController<*> && fragment.getTag() != null) {
                if (fragment.getTag()?.equals(tagName, ignoreCase = true) != true) {
                    beginTransaction().hide(fragment).commitNowAllowingStateLoss()
                }
            }
        }
        if (tag != null && tag is UIViewController<*>) {
            ft.setCustomAnimations(R.anim.bubble_in, R.anim.bubble_out)
            ft.show(tag).commit()
        } else if (newTab != null){
            ft.add(R.id.content, newTab, tagName).commit()
        }
        executePendingTransactions()
    }
}