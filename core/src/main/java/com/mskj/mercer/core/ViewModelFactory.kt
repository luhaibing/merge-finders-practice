package com.mskj.mercer.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author mercer
 */
class ViewModelFactory(private val view: OnRemindAction<*, *>) : ViewModelProvider.Factory {

    override fun <VM : ViewModel> create(clazz: Class<VM>): VM {
        return ViewModelFinder.get(clazz, view)
    }

}