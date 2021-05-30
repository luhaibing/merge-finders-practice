package com.mskj.mercer.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.mskj.mercer.core.OnRemindAction
import com.mskj.mercer.core.ViewBindingFactory
import com.mskj.mercer.core.ViewModelFactory

inline fun <reified VM : ViewModel> ComponentActivity.viewModelFinders(
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

inline fun <reified VB : ViewBinding> ComponentActivity.viewBindingFinders(): Lazy<VB> {
    return ViewBindingLazy(VB::class.java, { layoutInflater }, { null }, false)
}

inline fun <reified VB : ViewBinding> Fragment.viewBindingFinders(): Lazy<VB> {
    return ViewBindingLazy(
        VB::class.java,
        { LayoutInflater.from(requireContext()) },
        { null },
        false
    )
}

fun <VB : ViewBinding> Fragment.viewBindingFinders(zlass: () -> Class<VB>): Lazy<VB> {
    return ViewBindingLazy(zlass(), { LayoutInflater.from(requireContext()) }, { null }, false)
}

fun <VB : ViewBinding> ComponentActivity.viewBindingFinders(zlass: () -> Class<VB>): Lazy<VB> {
    return ViewBindingLazy(zlass(), { layoutInflater }, { null }, false)
}

class ViewBindingLazy<VB : ViewBinding>(
    private val kClass: Class<VB>,
    private val layoutInflater: () -> LayoutInflater,
    private val parent: () -> ViewGroup?,
    private val attachToParent: Boolean
) : Lazy<VB> {

    private var cached: VB? = null

    override val value: VB
        get() {
            return cached ?: ViewBindingFactory.get(
                kClass, layoutInflater(), parent(), attachToParent
            ).also {
                cached = it
            }
        }

    override fun isInitialized(): Boolean = cached != null

}