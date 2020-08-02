package com.example.mehrkalacoroutine.ui.adapter.horizontalRecycler

import androidx.recyclerview.widget.RecyclerView
import com.example.mehrkala.model.Boarder
import com.example.mehrkalacoroutine.databinding.RowBoarderBinding
import com.example.mehrkalacoroutine.ui.utils.OnClickHandler

class BoarderViewHolder(
    private val viewBinding:RowBoarderBinding
): RecyclerView.ViewHolder(viewBinding.root){
    fun <T> bind(boarder: Boarder, onClick: OnClickHandler<T>){
        viewBinding.boarder = boarder
        viewBinding.onClick = onClick
    }
}