package com.example.mehrkalacoroutine.ui.utils

import android.view.View
import com.example.mehrkalacoroutine.data.network.NetworkState

interface OnClickHandler<T>{
    fun onClick(element:T)
    fun onClickView(view:View , element:T)
}

interface OnItemClickHandler{
    fun onClick(view: View)
}

interface OnItemSelected<T>{
    fun onItemSelected(item:T)
}
