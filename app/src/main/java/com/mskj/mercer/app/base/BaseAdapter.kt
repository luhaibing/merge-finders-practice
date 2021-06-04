package com.mskj.mercer.app.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.mskj.mercer.core.ViewBindingFactory
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST", "unused", "MemberVisibilityCanBePrivate")
abstract class BaseAdapter<VB : ViewBinding, T >
    : RecyclerView.Adapter<BaseAdapter.ViewHolder<VB>>() {

    private val data: MutableList<T> by lazy { arrayListOf() }

    fun add(vararg items: T) {
        addAll(items.toList())
    }

    fun addAll(collection: Collection<T>) {
        data.addAll(collection)
    }

    fun getItem(position: Int): T? {
        if (position < 0 || position >= data.size) {
            return null
        }
        return data[position]
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder<VB>, position: Int) {
        holder.bind(getItem(position))
    }

    abstract fun ViewHolder<VB>.bind(item: T?)

    override fun onBindViewHolder(
        holder: ViewHolder<VB>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<VB> {
        val clazzVB = (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments
            .map { it as Class<*> }
            .find {
                ViewBinding::class.java.isAssignableFrom(it)
            } as Class<VB>
        return ViewHolder(
            ViewBindingFactory.get(clazzVB, LayoutInflater.from(parent.context), parent, false)
        )
    }

    class ViewHolder<VB : ViewBinding>(
        private val binding: VB,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun binding(): VB = binding

    }

}