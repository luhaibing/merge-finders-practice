package com.mskj.mercer.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mskj.mercer.app.databinding.FragmentItemBinding
import com.mskj.mercer.app.databinding.FragmentItemListBinding
import com.mskj.mercer.app.multiple.MultipleTypeAdapter
import com.mskj.mercer.app.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class ItemFragment : Fragment() {

    private val viewBinding by viewBindingFinders<FragmentItemListBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set the adapter
        if (view is RecyclerView) {
            println("111")
            with(view) {
                layoutManager = LinearLayoutManager(context)
                val value = MultipleTypeAdapter<PlaceholderContent.PlaceholderItem> {
                    addType<PlaceholderContent.Level, PlaceholderContent.PlaceholderItem, FragmentItemBinding>(
                        { position, item -> item.level == 0 }) { holder, item, _ ->
                        itemNumber.text = item.id
                        content.text = "This is at ${item.content} position : ${holder.adapterPosition}"
                    }
                    addType<PlaceholderContent.Level, PlaceholderContent.PlaceholderItem, FragmentItemBinding>(
                        { _, item ->
                            item.level == 1
                        }) { _, item, _ ->
                        itemNumber.text = "title"
                    }
                }
                value.addAll(PlaceholderContent.ITEMS)
                adapter = value
            }
        }
    }

}