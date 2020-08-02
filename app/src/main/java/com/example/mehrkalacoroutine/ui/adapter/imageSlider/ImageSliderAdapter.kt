package com.example.mehrkalacoroutine.ui.adapter.imageSlider

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mehrkala.model.Item
import com.example.mehrkalacoroutine.R
import com.example.mehrkalacoroutine.databinding.ImageSliderItemBinding
import com.example.mehrkalacoroutine.databinding.ImageViewItemBinding
import com.example.mehrkalacoroutine.ui.utils.OnClickHandler
import com.smarteist.autoimageslider.SliderViewAdapter

class ImageSliderAdapter<T>()
    :SliderViewAdapter<SliderViewAdapter.ViewHolder>(){

    var datas:List<T> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    var onClick:OnClickHandler<T> ?= null
    lateinit var layoutInflater:LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        layoutInflater = LayoutInflater.from(parent?.context)
        return when(getType()){
            R.layout.image_slider_item ->
                MainBoarderImageView(
                    ImageSliderItemBinding.inflate(layoutInflater, parent, false)
                )
            R.layout.image_view_item ->
                ShowImageViewHolder(
                    ImageViewItemBinding.inflate(layoutInflater, parent, false)
                )

            else -> throw IllegalArgumentException("unknown view type:")
        }
    }

    override fun getCount(): Int = datas.size

    override fun onBindViewHolder(viewHolder: ViewHolder?, position: Int) {
        when(getType()){
            R.layout.image_slider_item ->
                (viewHolder as MainBoarderImageView).bind(datas?.get(position) as Item , onClickHandler = onClick!!)
            R.layout.image_view_item ->
                (viewHolder as ShowImageViewHolder).bind(datas?.get(position) as String , onClickHandler = onClick!!)
        }
    }
    private fun getType(): Int {
        return if(datas.get(0) is Item)
             R.layout.image_slider_item
        else  if (datas.get(0) is String)
            R.layout.image_view_item
        else 1
    }

}