package com.example.mehrkalacoroutine.ui.adapter.horizontalRecycler

import androidx.recyclerview.widget.RecyclerView
import com.example.mehrkala.model.Item
import com.example.mehrkalacoroutine.databinding.RowReceiptBinding
import com.example.mehrkalacoroutine.ui.utils.OnClickHandler

class ReceiptViewHolder(
    private val viewBinder:RowReceiptBinding
): RecyclerView.ViewHolder(viewBinder.root){

    fun bind(item: Item){
        viewBinder.item = item
    }
}