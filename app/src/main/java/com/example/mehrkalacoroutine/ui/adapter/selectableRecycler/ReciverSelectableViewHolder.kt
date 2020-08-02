package com.example.mehrkalacoroutine.ui.adapter.selectableRecycler

import android.graphics.Color
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mehrkalacoroutine.data.network.model.ReciverInformation
import com.example.mehrkalacoroutine.databinding.RowSelectableReciverBinding
import com.example.mehrkalacoroutine.ui.utils.OnItemClickHandler
import com.example.mehrkalacoroutine.ui.utils.OnItemSelected

class ReciverSelectableViewHolder(
    val viewBinding:RowSelectableReciverBinding ,
    val onItemSelectedListener: OnItemSelected<ReciverInformation>
): RecyclerView.ViewHolder(viewBinding.root){
    var item:ReciverInformation = ReciverInformation()
    init{
        viewBinding.select = object: OnItemClickHandler {
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
                viewBinding.rowSelectableReciver.setBackgroundColor(Color.LTGRAY)
            else
                viewBinding.rowSelectableReciver.background = null

            item.isSelected = state
            viewBinding.rowSelectableReciver.isChecked = state
        }
    fun bind(reciver:ReciverInformation){
        viewBinding.reciver = reciver
        val typedValue = TypedValue()
        viewBinding.root.context.theme.resolveAttribute(android.R.attr.listChoiceIndicatorMultiple, typedValue , true)
        val checkMarkDrawableResId = typedValue.resourceId
        viewBinding.rowSelectableReciver.setCheckMarkDrawable(checkMarkDrawableResId)

        this.item = reciver
        setChecked(item.isSelected)
    }
}