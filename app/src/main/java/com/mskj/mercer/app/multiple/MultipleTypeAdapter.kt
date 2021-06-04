package com.mskj.mercer.app.multiple

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

//abstract
class MultipleTypeAdapter<T : Any>(block: Builder.() -> Unit) :
    RecyclerView.Adapter<MultipleTypeAdapter.ViewHolder>() {

    private val data: MutableList<T> = arrayListOf()

    private val types: MutableList<TypeBuilder> by lazy { arrayListOf() }/* = arrayListOf()*/

    init {
        val builder = Builder()
        builder.block()
        // types = arrayListOf()
        types.addAll(builder.types)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val find = types.find { it.viewType == viewType }
        return find!!.onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        val find = types.find { it.handle(position, data[position]) }
        find?.onBindViewHolder(holder, position, data[position], payloads)
    }

    override fun getItemViewType(position: Int): Int {
        val viewType = types.find { it.handle(position, data[position]) }!!.viewType
        return viewType
        // return super.getItemViewType(position)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(val viewBinding: ViewBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        inline fun <reified VB : ViewBinding> binding(): VB {
            return viewBinding as VB
        }
    }

    fun addAll(items: MutableList<T>) {
        data.addAll(items)
        notifyDataSetChanged()
    }

    companion object {

        private var index = 0

        @JvmStatic
        fun offset(): Int {
            return index++
        }

    }

}