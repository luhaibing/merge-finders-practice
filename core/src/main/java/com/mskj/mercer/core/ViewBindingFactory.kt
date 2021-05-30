package com.mskj.mercer.core

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

/**
 * @author mercer
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
object ViewBindingFactory {

    @JvmStatic
    fun <VB : ViewBinding> get(
        clazz: Class<VB>, layoutInflater: LayoutInflater,
        parent: ViewGroup?, attachToParent: Boolean
    ): VB {
        return ViewBindingFinder.get(
            // clazz::class.java.canonicalName,
            clazz, layoutInflater, parent, attachToParent
        )
    }

    @JvmStatic
    fun <VB : ViewBinding> get(
        clazz: KClass<VB>, layoutInflater: LayoutInflater,
        parent: ViewGroup?, attachToParent: Boolean
    ): VB {
        return get(clazz.java,layoutInflater, parent, attachToParent)
    }

    @JvmStatic
    fun <VB : ViewBinding> get(clazz: KClass<VB>, activity: Activity): VB {
        return get(clazz, LayoutInflater.from(activity), null, false)
    }

    @JvmStatic
    fun <VB : ViewBinding> get(clazz: KClass<VB>, fragment: Fragment): VB {
        return get(clazz, LayoutInflater.from(fragment.requireContext()), null, false)
    }

    @JvmStatic
    fun <VB : ViewBinding> get(clazz: KClass<VB>, fragment: android.app.Fragment): VB {
        return get(clazz, LayoutInflater.from(fragment.activity), null, false)
    }

    @JvmStatic
    fun <VB : ViewBinding> get(clazz: KClass<VB>, parent: ViewGroup): VB {
        return get(clazz, LayoutInflater.from(parent.context), null, false)
    }

    @JvmStatic
    inline fun <reified VB : ViewBinding> get(
        layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean
    ): VB {
        return get(VB::class, layoutInflater, parent, attachToParent)
    }

    @JvmStatic
    inline fun <reified VB : ViewBinding> get(activity: Activity): VB {
        return get(VB::class, LayoutInflater.from(activity), null, false)
    }

    @JvmStatic
    inline fun <reified VB : ViewBinding> get(fragment: Fragment): VB {
        return get(VB::class, LayoutInflater.from(fragment.requireContext()), null, false)
    }

    @JvmStatic
    inline fun <reified VB : ViewBinding> get(fragment: android.app.Fragment): VB {
        return get(VB::class, LayoutInflater.from(fragment.activity), null, false)
    }

    @JvmStatic
    inline fun <reified VB : ViewBinding> get(parent: ViewGroup): VB {
        return get(VB::class, LayoutInflater.from(parent.context), null, false)
    }

}