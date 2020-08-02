package com.example.mehrkalacoroutine.ui.adapter.imageSlider

import com.example.mehrkalacoroutine.databinding.ImageViewItemBinding
import com.example.mehrkalacoroutine.ui.utils.OnClickHandler
import com.smarteist.autoimageslider.SliderViewAdapter

class ShowImageViewHolder(
    private val itemViewBinding:ImageViewItemBinding
): SliderViewAdapter.ViewHolder(itemViewBinding.root){
    fun<T> bind(url:String , onClickHandler: OnClickHandler<T>){
        itemViewBinding.url = url
        itemViewBinding.onClick = onClickHandler
    }
}