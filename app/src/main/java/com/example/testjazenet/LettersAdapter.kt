package com.example.testjazenet

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.testjazenet.databinding.ItemLettersBinding


class LettersAdapter(private val clickListener: LetterClickListener) :
    RecyclerView.Adapter<LettersAdapter.ViewHolder>() {

    var selectedPos = -1
    val data: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLettersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, clickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(foldersData: ArrayList<String>) {
        data.clear()
        data.addAll(foldersData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemLettersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, clickListener: LetterClickListener) {
            binding.apply {
                name.text = data[position]
                if (selectedPos == position) {
                    rootLayout.setBackgroundResource(R.drawable.curved_black)
                    name.setTextColor(ContextCompat.getColor(name.context, R.color.white))
                } else {
                    rootLayout.setBackgroundResource(R.drawable.curved_gray)
                    name.setTextColor(ContextCompat.getColor(name.context, R.color.black))
                }
                binding.rootLayout.setOnClickListener {
                    clickListener.onClick(
                        data[position],
                        position
                    )
                }
            }
            binding.executePendingBindings()
        }
    }
}


class LetterClickListener(val clickListener: (data: String, position: Int) -> Unit) {
    fun onClick(data: String, position: Int) = clickListener(data, position)
}
