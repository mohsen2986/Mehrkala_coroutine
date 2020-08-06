package com.example.mehrkalacoroutine.ui.fragment.paymentgateway

import androidx.lifecycle.ViewModel
import com.example.mehrkalacoroutine.data.repository.OrdersRepository

class PaymentGatewayViewModel(
    private val order: OrdersRepository
) : ViewModel() {
    suspend fun sendPaymentInfo(refId:String) =
        order.sendPaymentInfo(refId)
}
