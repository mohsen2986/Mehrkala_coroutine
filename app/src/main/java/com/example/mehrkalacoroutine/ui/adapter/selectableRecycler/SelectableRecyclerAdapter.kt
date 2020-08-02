package com.example.mehrkalacoroutine.ui.adapter.selectableRecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mehrkalacoroutine.R
import com.example.mehrkalacoroutine.data.network.model.Address
import com.example.mehrkalacoroutine.data.network.model.ReciverInformation
import com.example.mehrkalacoroutine.databinding.RowSelectableItemBinding
import com.example.mehrkalacoroutine.databinding.RowSelectableReciverBinding
import com.example.mehrkalacoroutine.ui.utils.OnClickHandler
import com.example.mehrkalacoroutine.ui.utils.OnItemSelected

class SelectableRecyclerAdapter<T>(
    private val datas:List<T> = listOf() ,
    private val listener:OnItemSelected<T>
    ):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private lateinit var  layoutInflater :LayoutInflater
    private val rowListener = setItemListener()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType) {
             R.layout.row_selectable_item -> AddressSelectableViewHolder(
                 RowSelectableItemBinding.inflate( layoutInflater , parent , false) , rowListener as OnItemSelected<Address>)
            R.layout.row_selectable_reciver -> ReciverSelectableViewHolder(
                RowSelectableReciverBinding.inflate( layoutInflater , parent , false ), rowListener as OnItemSelected<ReciverInformation>)

               else -> throw IllegalArgumentException("unknown view type: $viewType")
        }
    }

    override fun getItemCount(): Int =
        datas.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when(holder){
            is AddressSelectableViewHolder -> holder.bind(datas[position] as Address)
            is ReciverSelectableViewHolder -> holder.bind(datas[position] as ReciverInformation)
            else -> throw IllegalArgumentException("unknown view holder: $holder")
        }

    override fun getItemViewType(position: Int): Int =
        when(datas[position]){
            is Address -> R.layout.row_selectable_item
            is ReciverInformation -> R.layout.row_selectable_reciver
            else -> throw IllegalArgumentException("unknown view type: ${datas[position]}")
        }
    private fun setItemListener(): Any =
         when(datas[0]){
            is Address -> object:OnItemSelected<Address>{
                override fun onItemSelected(item: Address) {
                    (datas as List<Address>).forEach {
                        if (it.isSelected && it != item)
                            it.isSelected = false
                        else if (item.isSelected && it == item)
                            it.isSelected = true

                        notifyDataSetChanged()
                    }
                    listener.onItemSelected(item as T)
                }
            }
             is ReciverInformation -> object:OnItemSelected<ReciverInformation> {
                 override fun onItemSelected(item: ReciverInformation) {
                     (datas as List<ReciverInformation>).forEach {
                         if (it.isSelected && it != item)
                             it.isSelected = false
                         else if (item.isSelected && it == item)
                             it.isSelected = true

                         notifyDataSetChanged()
                     }
                     listener.onItemSelected(item as T)
                 }
             }
            else -> throw IllegalArgumentException("setItemListener is not impelemnted")
        }
    }
