package com.mskj.mercer.app.multiple

import android.view.ViewGroup

abstract class TypeBuilder {

    abstract fun handle(position: Int,t:Any): Boolean

    abstract val viewType: Int

    abstract fun onCreateViewHolder(parent: ViewGroup): MultipleTypeAdapter.ViewHolder

    abstract fun onBindViewHolder(
        holder: MultipleTypeAdapter.ViewHolder, position: Int, item: Any?, payloads: MutableList<Any>
    )

}

