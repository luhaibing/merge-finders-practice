package com.mskj.mercer.app

import android.annotation.SuppressLint
import com.mskj.mercer.app.base.BaseAdapter

import com.mskj.mercer.app.placeholder.PlaceholderContent.PlaceholderItem
import com.mskj.mercer.app.databinding.FragmentItemBinding

/**
 *
 */
class MyItemRecyclerViewAdapter : BaseAdapter<FragmentItemBinding, PlaceholderItem>() {

    @SuppressLint("SetTextI18n")
    override fun ViewHolder<FragmentItemBinding>.bind(item: PlaceholderItem?) {
      item?.let {
          binding().itemNumber.text = it.id
          binding().content.text = "This is at ${it.content} position : $adapterPosition"
      }
    }

    override fun onBindViewHolder(holder: ViewHolder<FragmentItemBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
    }

}