package com.example.mehrkalacoroutine.ui.adapter.horizontalRecycler

import androidx.recyclerview.widget.RecyclerView
import com.example.mehrkala.model.Item
import com.example.mehrkalacoroutine.databinding.RowTopItemBinding
import com.example.mehrkalacoroutine.ui.utils.OnClickHandler

class TopItemViewHolder(
    private val itemViewBinding:RowTopItemBinding
): RecyclerView.ViewHolder(itemViewBinding.root){
    fun <T>bind(item: Item ,onClick:OnClickHandler<T>){
        itemViewBinding.item = item
        itemViewBinding.onClick = onClick
    }
}