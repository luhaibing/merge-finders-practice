package com.mskj.mercer.app

import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "UNCHECKED_CAST")
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected val binding: VB by viewBindingFinders {
        (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments
            .map { Class.forName((it as Class<*>).canonicalName) }
            .first { clazz -> clazz.interfaces.find { it.toString() == ViewBinding::class.java.toString() } != null } as Class<VB>
    }

}