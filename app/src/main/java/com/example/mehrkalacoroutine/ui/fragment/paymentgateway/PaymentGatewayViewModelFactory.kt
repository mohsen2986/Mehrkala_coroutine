package com.example.mehrkalacoroutine.ui.fragment.paymentgateway

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mehrkalacoroutine.data.repository.OrdersRepository

class PaymentGatewayViewModelFactory(
    private val order: OrdersRepository
    ): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PaymentGatewayViewModel(order) as T
    }
}