package com.mskj.mercer.app.multiple

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.mskj.mercer.core.ViewBindingFactory
import java.lang.reflect.ParameterizedType

//open
abstract class TypeBuilderImpl<VB : ViewBinding, T : Any> : TypeBuilder() {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup): MultipleTypeAdapter.ViewHolder {
        val viewBindingClzss = (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments
            .map { it as Class<*> }
            .find {
                ViewBinding::class.java.isAssignableFrom(it)
            } as Class<VB>
       val binding = ViewBindingFactory.get(
            viewBindingClzss, LayoutInflater.from(parent.context), parent, false
        )
        return MultipleTypeAdapter.ViewHolder(binding)
    }

}