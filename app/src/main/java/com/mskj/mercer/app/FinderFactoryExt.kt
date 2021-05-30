package com.mskj.mercer.app

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.mskj.mercer.core.OnRemindAction
import com.mskj.mercer.core.ViewModelFactory

public inline fun <reified VM : ViewModel> ComponentActivity.viewModelFinders(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        if (this is OnRemindAction<*, *>) {
            ViewModelFactory(this)
        } else {
            defaultViewModelProviderFactory
        }
    }
    return ViewModelLazy(VM::class, { viewModelStore }, factoryPromise)
}