package com.mskj.mercer.app.multiple

import androidx.viewbinding.ViewBinding

class Builder {

    val types: MutableList<TypeBuilder> = arrayListOf()

    inline fun <reified T : Any, reified VB : ViewBinding> addType(crossinline block: VB.(
        holder: MultipleTypeAdapter.ViewHolder, item: T, payloads: MutableList<Any>) -> Unit) {
        addType({ position: Int, item: Any -> item is T }, block)
    }

    inline fun <reified P : Any, reified T : P, reified VB : ViewBinding> addType(
        crossinline handler: (position: Int, t: P) -> Boolean,
        crossinline block: VB.(holder: MultipleTypeAdapter.ViewHolder, item: T,payloads: MutableList<Any>) -> Unit
    ) {
        types.add(object : TypeBuilderImpl<VB, T>() {
            override fun handle(position: Int, t: Any): Boolean {
                return handler(position, t as P)
            }

            override fun onBindViewHolder(
                holder: MultipleTypeAdapter.ViewHolder,
                position: Int, item: Any?, payloads: MutableList<Any>
            ) {
                holder.binding<VB>().block(holder, item as T, payloads)
            }

            override val viewType: Int by lazy {
                MultipleTypeAdapter.offset()
            }
        })
    }

}

