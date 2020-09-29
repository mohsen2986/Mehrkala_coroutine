
package com.example.mehrkalacoroutine.ui.fragment.sendBillsBasket

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mehrkalacoroutine.data.network.model.Address
import com.example.mehrkalacoroutine.data.network.model.ReciverInformation
import com.example.mehrkalacoroutine.data.repository.OrdersRepository
import com.example.mehrkalacoroutine.ui.base.lazyDeferred

class SendBillsBasketViewModel(
    private val order: OrdersRepository
) : ViewModel() {

    suspend fun getPostReceipt(type :Int, state :String) =
        order.getPostReceipt(type , state)

    suspend fun addAddress(address:String , postNumber:String) =
        order.addAddress(address , postNumber)

    suspend fun addReciverInfo(name:String , phoneNumber:String) =
        order.addInfo(name , phoneNumber)

    val addresses by lazyDeferred {
        order.getAddress()
    }
    suspend fun getAddresses() =
        order.getAddress()

    val reciverInformation by lazyDeferred{
        order.getReciverInfo()
    }
    suspend fun getReciverInformation() =
        order.getReciverInfo()

    val receipt by lazyDeferred{
        order.getReceipt()
    }
    fun setAddress(address: Address) {
        order.address = address
    }
    fun setReciver(reciver: ReciverInformation){
        order.reciver = reciver
    }
    fun addressIsValid() =
        order.addressIsValid()
    fun reciverIsValid() =
        order.reciverIsValid()

}