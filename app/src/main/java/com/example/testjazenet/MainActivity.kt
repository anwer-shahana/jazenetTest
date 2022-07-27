package com.example.testjazenet

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.testjazenet.base.toArrayList
import com.example.testjazenet.base.toast
import com.example.testjazenet.databinding.ActivityMainBinding
import com.example.testjazenet.provider.ContactAdapterModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: MainActivityAdapter
    private lateinit var lettersAdapter: LettersAdapter
    private lateinit var layoutManager: LinearLayoutManagerWithSmoothScroller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupRecyclerView()
        setupObserver()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupRecyclerView() {
        layoutManager = LinearLayoutManagerWithSmoothScroller(this)
        adapter = MainActivityAdapter()
        binding.itemRecycler.layoutManager = layoutManager
        binding.itemRecycler.adapter = adapter


        lettersAdapter = LettersAdapter(LetterClickListener { letter, pos ->
            val indexPos = adapter.itemList.indexOfFirst {
                it.letter == letter && it.type == "head"
            }
            lettersAdapter.selectedPos = pos
            lettersAdapter.notifyDataSetChanged()
            binding.itemRecycler.smoothScrollToPosition(indexPos)
        })
        binding.lettersRecycler.adapter = lettersAdapter
    }

    private fun setupObserver() {
        viewModel.getContactResponse().observe(this) {

            val list = it.map { it.name?.trim()?.first().toString().uppercase() }.toArrayList()
            val map = it.groupBy { it.name?.trim()?.first().toString().uppercase() }
            val newList: ArrayList<ContactAdapterModel> = ArrayList()
            map.forEach { (key, value) ->
                newList.add(ContactAdapterModel("head", key))
                value.forEach { contact ->
                    newList.add(ContactAdapterModel("item", null, contact))
                }
            }

            lettersAdapter.selectedPos = 0
            lettersAdapter.submitList(list.distinct().toArrayList())
            adapter.submitList(newList)
        }
        viewModel.getError().observe(this) {
            toast(it)
        }
    }
}