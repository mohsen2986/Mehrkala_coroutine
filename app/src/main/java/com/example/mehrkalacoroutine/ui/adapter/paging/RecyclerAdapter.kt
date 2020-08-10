package com.example.mehrkalacoroutine.ui.adapter.paging

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mehrkala.model.Item
import com.example.mehrkalacoroutine.R
import com.example.mehrkalacoroutine.data.network.NetworkState
import com.example.mehrkalacoroutine.data.network.model.OrdersHistory
import com.example.mehrkalacoroutine.databinding.RowBasketBinding
import com.example.mehrkalacoroutine.databinding.RowItemBinding
import com.example.mehrkalacoroutine.databinding.RowLoadingBinding
import com.example.mehrkalacoroutine.databinding.RowOrdersBinding
import com.example.mehrkalacoroutine.ui.utils.OnClickHandler

class RecyclerAdapter<T> (
    private val callback: OnClickListener
): PagedListAdapter< T, RecyclerView.ViewHolder>(
    object: DiffUtil.ItemCallback<T>(){
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem == newItem
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem == newItem
    }
){

//    companion object {
//        private val diffCallBack = object: DiffUtil.ItemCallback<Item>(){
//            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
//                oldItem == newItem
//            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
//                oldItem == newItem
//        }
//    }
    // recycler layers
    private val layers :Map<Any , Int> = mapOf(
        R.layout.row_item to 1 ,
        R.layout.row_loading to 2
    )


    // FOR DATA--
    lateinit var onClickHandler: OnClickHandler<Any>
    var animation: Int? = null
    private var networkState: NetworkState? = null
    interface OnClickListener{
        fun onRefresh()
        fun whenListIsUpdated(size :Int , networkState: NetworkState?)
    }

    // OVERRIDE ---
    private lateinit var layoutInflater:LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
        println("debug: $viewType")
        return when(viewType){
            R.layout.row_item -> ItemViewHolder(
                RowItemBinding.inflate(layoutInflater, parent, false)
            )
            R.layout.row_loading -> LoadingViewHolder(
                RowLoadingBinding.inflate(layoutInflater, parent, false)
            )
            R.layout.row_basket -> BasketViewHolder(
                RowBasketBinding.inflate(layoutInflater , parent , false)
            )
            R.layout.row_orders -> OrdersHistoryViewHolder(
                RowOrdersBinding.inflate(layoutInflater , parent , false)
            )
            else -> throw IllegalArgumentException("unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            R.layout.row_loading -> (holder as LoadingViewHolder).bind()
            R.layout.row_item -> (holder as ItemViewHolder).bind(getItem(position) as Item , onClick = onClickHandler)
            R.layout.row_basket -> (holder as BasketViewHolder).bind(getItem(position) as Item , onClickHandler)
            R.layout.row_orders -> (holder as OrdersHistoryViewHolder).bind(getItem(position) as OrdersHistory , onClick = onClickHandler , animation = animation)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(hasExtraRow() && position == itemCount-1)
            R.layout.row_loading
        else if(getItem(0) is OrdersHistory)
            R.layout.row_orders
        else if((getItem(0) as Item).count == 0)
            R.layout.row_item
        else
            R.layout.row_basket
    }

    override fun getItem(position: Int): T? {
        this.callback.whenListIsUpdated(super.getItemCount() , this.networkState )
        return super.getItem(position)
    }
    // UTILS ---
    private fun hasExtraRow() = networkState != null && networkState != NetworkState.SUCCESS

    // public API
    fun updateNetworkState(networkState: NetworkState){
        val previousNetworkState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = networkState
        val hasExtraRow = hasExtraRow()
        if(hasExtraRow != hadExtraRow){
            if (hadExtraRow)  notifyItemRemoved(super.getItemCount())
            else              notifyItemInserted(super.getItemCount())
        }else if(hadExtraRow && previousNetworkState != networkState){
            notifyItemChanged(itemCount -1)
        }
    }

}