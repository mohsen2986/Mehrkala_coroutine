package com.example.mehrkalacoroutine.ui.adapter.paging

import androidx.recyclerview.widget.RecyclerView
import com.example.mehrkala.model.Item
import com.example.mehrkalacoroutine.databinding.RowBasketBinding
import com.example.mehrkalacoroutine.ui.utils.OnClickHandler

class BasketViewHolder(
    private val viewBinding:RowBasketBinding
): RecyclerView.ViewHolder(viewBinding.root){
    fun <T>bind(item:Item , onClick:OnClickHandler<T>){
        viewBinding.item = item
        viewBinding.onClick = onClick
    }
}