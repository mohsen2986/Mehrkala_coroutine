package com.example.mehrkalacoroutine.ui.adapter.paging

import androidx.recyclerview.widget.RecyclerView
import com.example.mehrkala.model.Item
import com.example.mehrkalacoroutine.databinding.RowItemBinding
import com.example.mehrkalacoroutine.ui.utils.OnClickHandler


class ItemViewHolder(
    private val itemViewBinding: RowItemBinding
):RecyclerView.ViewHolder(itemViewBinding.root){

    fun<T> bind(item:Item ,onClick: OnClickHandler<T>){
        itemViewBinding.item = item
        itemViewBinding.onClick = onClick
    }
}