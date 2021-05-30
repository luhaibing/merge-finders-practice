package com.mskj.mercer.core

import androidx.lifecycle.ViewModel
import com.mskj.mercer.core.impl.OnEmptyRemindActionImpl

/**
 * @author mercer
 */
@Suppress("unused")
open class BaseViewModel : ViewModel() {

    private var action: OnRemindAction<*, *>? = null

    private fun action(): OnRemindAction<*, *> {
        return action ?: OnEmptyRemindActionImpl()
    }

   open fun attach(remind: OnRemindAction<*, *>) {
        action = remind
    }

    override fun onCleared() {
        super.onCleared()
        action().onCleared()
    }

}