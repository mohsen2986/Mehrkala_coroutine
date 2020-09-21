package com.example.mehrkalacoroutine.ui.adapter.selectableRecycler

import android.graphics.Color
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mehrkalacoroutine.data.model.PacketType
import com.example.mehrkalacoroutine.data.network.model.Address
import com.example.mehrkalacoroutine.databinding.RowSelectableItemBinding
import com.example.mehrkalacoroutine.databinding.RowSelectableTypeBinding
import com.example.mehrkalacoroutine.ui.utils.OnItemClickHandler
import com.example.mehrkalacoroutine.ui.utils.OnItemSelected

class PacketTypeSelectableViewHolder(
    private val viewBinding:RowSelectableTypeBinding ,
    var onItemSelectedListener: OnItemSelected<PacketType>
    ): RecyclerView.ViewHolder(viewBinding.root){

    var item: PacketType = PacketType()
    init {
        viewBinding.select = object:OnItemClickHandler{
            override fun onClick(view: View) {
                if (item.isSelected)
                    setChecked(false)
                else
                    setChecked(true)

                onItemSelectedListener.onItemSelected(item)
            }
        }
    }
    private fun setChecked(state:Boolean){
        if (state)
            viewBinding.rowSelectableItem.setBackgroundColor(Color.LTGRAY)
        else
            viewBinding.rowSelectableItem.background = null

        item.isSelected = state
        viewBinding.rowSelectableItem.isChecked = state
    }

    fun bind(packetType :PacketType){
        viewBinding.data = packetType
        val typedValue = TypedValue()
        viewBinding.root.context.theme.resolveAttribute(android.R.attr.listChoiceIndicatorMultiple, typedValue , true)
        val checkMarkDrawableResId = typedValue.resourceId
        viewBinding.rowSelectableItem.setCheckMarkDrawable(checkMarkDrawableResId)

        this.item = packetType
        setChecked(item.isSelected)
    }
}