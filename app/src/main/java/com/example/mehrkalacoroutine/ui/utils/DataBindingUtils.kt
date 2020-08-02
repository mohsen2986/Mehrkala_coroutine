package com.example.mehrkalacoroutine.ui.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class DataBindingUtils () {
companion object {
    @JvmStatic
    @BindingAdapter("android:loadImage")
    fun loadImage(imageView: ImageView, url: String?) {
        if(url != null)
            Glide
                .with(imageView.context)
                .load("http://www.paarandco.ir/mehrkala/$url")
                .into(imageView)
    }
}
}