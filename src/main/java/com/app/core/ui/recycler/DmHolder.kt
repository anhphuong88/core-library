package com.app.core.ui.recycler

import com.app.core.model.DmBase

class DmHolder<T> : DmBase {
    var lst: List<T?>? = null
    var fn: String? = null
    var payLoad: Any? = null
    var index: Int = -1

    constructor(fn: String) {
        this.fn = fn
    }

    constructor(fn: String?, value: List<T?>?) {
        this.lst = value
        this.fn = fn
    }

    constructor(fn: String?, index: Int, payLoad: Any?) {
        this.fn = fn
        this.payLoad = payLoad
        this.index = index
    }
}
