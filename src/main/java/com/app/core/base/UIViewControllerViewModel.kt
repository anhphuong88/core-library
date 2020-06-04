package com.app.core.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

open class UIViewControllerViewModel(application: Application) : AndroidViewModel(application) {
    var typeNavigation: AnimatedNavigation = AnimatedNavigation.None

    open fun dispose() {

    }
}
