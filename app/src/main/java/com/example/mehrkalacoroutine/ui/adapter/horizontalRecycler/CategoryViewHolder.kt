package com.example.mehrkalacoroutine.ui.adapter.horizontalRecycler

import androidx.recyclerview.widget.RecyclerView
import com.example.mehrkalacoroutine.data.network.model.Category
import com.example.mehrkalacoroutine.databinding.RowCategoryBinding
import com.example.mehrkalacoroutine.ui.utils.OnClickHandler

class CategoryViewHolder(
    private val viewBinding:RowCategoryBinding
    ): RecyclerView.ViewHolder(viewBinding.root){
    fun <T> bind(category: Category, onClick:OnClickHandler<T>){
        viewBinding.category = category
        viewBinding.onClick = onClick
    }
}