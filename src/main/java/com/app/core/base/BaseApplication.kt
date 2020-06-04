package com.app.core.base

import android.app.Application
import com.app.core.db.Pref

import com.app.core.db.RefStorage
import com.app.core.ui.image.Config
import com.app.core.util.NetUtil

open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NetUtil.init(applicationContext)
        RefStorage.init(applicationContext)
        Pref.init(applicationContext)
        Config.init(this)
    }
}
