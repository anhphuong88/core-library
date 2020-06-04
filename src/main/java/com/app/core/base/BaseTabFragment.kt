package com.app.core.base

import android.arch.lifecycle.ViewModelProviders
import android.widget.RelativeLayout

import java.lang.ref.WeakReference

class BaseTabFragment : UIViewController<BaseTabFragmentViewModel>() {

    private var child: WeakReference<UIViewController<*>>? = null

    override fun createViewModel(): BaseTabFragmentViewModel {
        return ViewModelProviders.of(this).get(BaseTabFragmentViewModel::class.java)
    }

    override fun didFragment(main: RelativeLayout) {
        child?.get()?.let {
            replace(it)
        }
    }

    companion object {

        fun create(child: UIViewController<*>): BaseTabFragment {
            val tab = BaseTabFragment()
            tab.child = WeakReference(child)
            return tab
        }
    }
}
