package com.example.mehrkalacoroutine.ui.fragment.paymentgateway

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PaymentGatewayViewModelFactory(
    ): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PaymentGatewayViewModel() as T
    }
}