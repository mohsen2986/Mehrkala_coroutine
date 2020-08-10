package com.example.mehrkalacoroutine.ui.adapter.paging

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.mehrkalacoroutine.data.network.model.OrdersHistory
import com.example.mehrkalacoroutine.databinding.RowOrdersBinding
import com.example.mehrkalacoroutine.ui.utils.OnClickHandler
import kotlinx.android.synthetic.main.row_orders.view.*


class OrdersHistoryViewHolder(
    private val viewBinding: RowOrdersBinding
): RecyclerView.ViewHolder(viewBinding.root){
    fun <T>bind(ordersHistory: OrdersHistory , onClick : OnClickHandler<T> , animation: Int?){
        viewBinding.order = ordersHistory
        viewBinding.onClick = onClick
        if(animation != null)
            viewBinding.root.row_orders_container.animation = AnimationUtils.loadAnimation(viewBinding.root.context , animation)

    }

}