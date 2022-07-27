package com.example.testjazenet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testjazenet.databinding.ItemHeadBinding
import com.example.testjazenet.databinding.ItemInitialHeaderLayoutBinding
import com.example.testjazenet.provider.CompanyContactList
import com.example.testjazenet.provider.ContactAdapterModel
import com.example.testjazenet.provider.ContactDetailsProvider

class MainActivityAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     var itemList: ArrayList<ContactAdapterModel> = ArrayList()

    fun submitList(list: ArrayList<ContactAdapterModel>) {
        itemList = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position].type) {
            "head" -> 101
            else -> 102
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            101 -> HeadViewHolder.from(parent)
            else -> ViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            101 -> (holder as HeadViewHolder).bind(itemList[position], position)
            else -> (holder as ViewHolder).bind(itemList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder private constructor(private val binding: ItemInitialHeaderLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContactAdapterModel, position: Int) {
            binding.viewModel = item.item
            binding.position = position

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemInitialHeaderLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


    class HeadViewHolder private constructor(private val binding: ItemHeadBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContactAdapterModel, position: Int) {
            binding.name.text = item.letter
        }

        companion object {
            fun from(parent: ViewGroup): HeadViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHeadBinding.inflate(layoutInflater, parent, false)
                return HeadViewHolder(binding)
            }
        }
    }


}