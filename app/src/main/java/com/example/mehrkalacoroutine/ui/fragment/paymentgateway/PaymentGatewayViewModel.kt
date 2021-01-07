package com.example.mehrkalacoroutine.ui.fragment.paymentgateway

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mehrkalacoroutine.data.network.model.Address
import com.example.mehrkalacoroutine.data.network.model.ReciverInformation
import com.example.mehrkalacoroutine.data.repository.OrdersRepository

class PaymentGatewayViewModel(
    private val order: OrdersRepository
) : ViewModel() {
    suspend fun sendPaymentInfo(refId:String) =
        order.sendPaymentInfo(refId)

    fun address() =
        order.address
    fun reciver() =
        order.reciver
    fun resetOrderInformation(){
        order.address = Address()
        order.reciver = ReciverInformation()
    }
    suspend fun downloadReceipt(context: Context) =
        order.downloadReceiptPdf(context)
}
