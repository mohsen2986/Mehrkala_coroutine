package com.example.mehrkalacoroutine.ui.adapter.horizontalRecycler

import androidx.recyclerview.widget.RecyclerView
import com.example.mehrkala.model.Boarder
import com.example.mehrkalacoroutine.data.network.model.CategoryImage
import com.example.mehrkalacoroutine.databinding.RowCategoryImgBinding
import com.example.mehrkalacoroutine.ui.utils.OnClickHandler

class CategoryImageViewHolder(
    private val viewBinding:RowCategoryImgBinding
): RecyclerView.ViewHolder(viewBinding.root){
    fun <T> bind(category: CategoryImage, onClick: OnClickHandler<T>) {
        viewBinding.category = category
        viewBinding.onClick = onClick
    }
}