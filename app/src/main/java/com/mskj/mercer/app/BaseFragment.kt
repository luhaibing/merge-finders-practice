package com.mskj.mercer.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "UNCHECKED_CAST")
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected val binding: VB by viewBindingFinders {
        (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments
            .map { Class.forName((it as Class<*>).canonicalName) }
            .first { clazz -> clazz.interfaces.find { it.toString() == ViewBinding::class.java.toString() } != null } as Class<VB>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // return super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

}