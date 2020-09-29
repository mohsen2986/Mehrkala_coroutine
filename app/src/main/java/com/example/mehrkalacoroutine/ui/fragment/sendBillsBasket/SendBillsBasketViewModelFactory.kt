package com.example.mehrkalacoroutine.ui.fragment.sendBillsBasket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mehrkalacoroutine.data.repository.OrdersRepository

class SendBillsBasketViewModelFactory(
    private val order : OrdersRepository
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SendBillsBasketViewModel(order) as T
    }
}
