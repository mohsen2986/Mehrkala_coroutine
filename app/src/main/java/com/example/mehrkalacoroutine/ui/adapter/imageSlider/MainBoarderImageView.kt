package com.example.mehrkalacoroutine.ui.adapter.imageSlider

import com.example.mehrkala.model.Item
import com.example.mehrkalacoroutine.databinding.ImageSliderItemBinding
import com.example.mehrkalacoroutine.ui.utils.OnClickHandler
import com.smarteist.autoimageslider.SliderViewAdapter
class MainBoarderImageView (
    private val itemViewBinding: ImageSliderItemBinding
):SliderViewAdapter.ViewHolder(itemViewBinding.root){

    fun <T> bind(item: Item, onClickHandler:OnClickHandler<T>){
        itemViewBinding.item = item
        itemViewBinding.onClick = onClickHandler
//        Glide
//            .with(itemView)
//            .load("http://www.paarandco.ir/mehrkala/"+item.image)
//            .skipMemoryCache(false)
//            .transition(DrawableTransitionOptions.withCrossFade(400))
//            .diskCacheStrategy(DiskCacheStrategy.DATA)
//            .into(imageView)
    }
}