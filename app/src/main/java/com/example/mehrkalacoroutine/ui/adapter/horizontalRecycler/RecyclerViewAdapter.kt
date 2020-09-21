package com.example.mehrkalacoroutine.ui.adapter.horizontalRecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mehrkala.model.Boarder
import com.example.mehrkala.model.Item
import com.example.mehrkalacoroutine.R
import com.example.mehrkalacoroutine.data.network.model.Category
import com.example.mehrkalacoroutine.data.network.model.CategoryImage
import com.example.mehrkalacoroutine.databinding.*
import com.example.mehrkalacoroutine.ui.adapter.paging.ItemViewHolder
import com.example.mehrkalacoroutine.ui.utils.OnClickHandler

class RecyclerViewAdapter<T>(
):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private lateinit var layoutInflater:LayoutInflater
    // FOR DATA --
    var datas:MutableList<T> = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    var boarder:Boarder ?= null

    lateinit var onClick: OnClickHandler<T>
    lateinit var onBoarderClick : OnClickHandler<Boarder>

    var itemType = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType){
            R.layout.row_top_item ->
                TopItemViewHolder(
                    RowTopItemBinding.inflate(layoutInflater, parent, false)
                )
            R.layout.row_boarder ->
                BoarderViewHolder(
                    RowBoarderBinding.inflate(layoutInflater, parent, false)
                )
            R.layout.row_category ->
                CategoryViewHolder(
                    RowCategoryBinding.inflate(layoutInflater , parent , false)
                )
            R.layout.row_receipt ->
                ReceiptViewHolder(
                    RowReceiptBinding.inflate(layoutInflater , parent , false)
                )
            R.layout.row_category_img ->
                CategoryImageViewHolder(
                    RowCategoryImgBinding.inflate(layoutInflater , parent , false)
                )
            R.layout.row_item ->
                ItemViewHolder(
                    RowItemBinding.inflate(layoutInflater , parent , false)
                )

            else -> throw IllegalArgumentException("unknown view type:$viewType")
        }
    }

    override fun getItemCount(): Int = if (boarder == null )datas.size else datas.size+1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when(holder) {
            is BoarderViewHolder -> holder.bind(boarder!!, onBoarderClick)
            is TopItemViewHolder -> holder.bind((if(itemType ==3) datas[position] else datas[position - 1]) as Item, onClick = onClick) // todo if(itemType ==1) datas[position] else
            is CategoryViewHolder-> holder.bind(datas[position] as Category, onClick = onClick)
            is ReceiptViewHolder -> holder.bind(datas[position] as Item)
            is CategoryImageViewHolder -> holder.bind(datas[position] as CategoryImage , onClick = onClick)
            is ItemViewHolder -> holder.bind(datas[position] as Item , onClick = onClick)
            else -> throw IllegalArgumentException("unknown holse:$holder")
        }

    override fun getItemViewType(position: Int): Int =
        if( boarder != null && position == 0)
                R.layout.row_boarder
        else if (datas[0] is Item && (boarder != null || itemType ==3))
                R.layout.row_top_item
        else if(datas[0] is Category)
                R.layout.row_category
        else if(datas[0] is Item && itemType == 2)
            R.layout.row_item
        else if(datas[0] is Item)
            R.layout.row_receipt
        else if(datas[0] is CategoryImage)
            R.layout.row_category_img
        else
            throw throw IllegalArgumentException("unknown viewType")
}