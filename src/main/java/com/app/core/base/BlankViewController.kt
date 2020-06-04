package com.app.core.base

import android.arch.lifecycle.ViewModelProviders

open class BlankViewController : UIViewController<BlankViewControllerViewModel>() {
    override fun createViewModel(): BlankViewControllerViewModel {
        return ViewModelProviders.of(this).get(BlankViewControllerViewModel::class.java)
    }
}
