package com.example.mehrkalacoroutine.ui.adapter.paging

import androidx.recyclerview.widget.RecyclerView
import com.example.mehrkalacoroutine.data.network.model.OrdersHistory
import com.example.mehrkalacoroutine.databinding.RowOrdersBinding
import com.example.mehrkalacoroutine.ui.utils.OnClickHandler

class OrdersHistoryViewHolder(
    private val viewBinding: RowOrdersBinding
): RecyclerView.ViewHolder(viewBinding.root){
    fun <T>bind(ordersHistory: OrdersHistory , onClick : OnClickHandler<T>){
        viewBinding.order = ordersHistory
        viewBinding.onClick = onClick
    }
}