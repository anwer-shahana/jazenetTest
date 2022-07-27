package com.example.testjazenet.bindingAdapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.testjazenet.R

import timber.log.Timber

@BindingAdapter("loadImageFromUrl")
fun loadImageFromUrl(imgView: ImageView, url: String?) {
    Timber.d("url --> $url")
    Glide.with(imgView.context)
        .load(url)
        .circleCrop()
        .apply(
            RequestOptions().placeholder(R.drawable.dummy_img)
                .error(R.drawable.dummy_img)
                .override(512, 512)
        ).into(imgView)
}