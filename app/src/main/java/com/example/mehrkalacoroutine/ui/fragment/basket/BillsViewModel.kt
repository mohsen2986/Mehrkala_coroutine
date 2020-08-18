package com.example.mehrkalacoroutine.ui.fragment.basket

import androidx.lifecycle.ViewModel
import com.example.mehrkalacoroutine.data.network.api.ApiInterface
import com.example.mehrkalacoroutine.data.network.model.Address
import com.example.mehrkalacoroutine.data.network.model.ReciverInformation
import com.example.mehrkalacoroutine.data.repository.OrdersRepository
import com.example.mehrkalacoroutine.ui.base.lazyDeferred

class BillsViewModel(
    private val order:OrdersRepository
) : ViewModel() {
    suspend fun addAddress(address:String , postNumber:String) =
        order.addAddress(address , postNumber)

    suspend fun addReciverInfo(name:String , phoneNumber:String) =
        order.addInfo(name , phoneNumber)

    val addresses by lazyDeferred {
        order.getAddress()
    }
    val reciverInformation by lazyDeferred{
        order.getReciverInfo()
    }
    val receipt by lazyDeferred{
        order.getReceipt()
    }
    fun setAddress(address: Address) {
        order.address = address
    }
    fun setReciver(reciver:ReciverInformation){
        order.reciver = reciver
    }
//    fun addressIsSet() =
//        order.addressIsSet()
//    fun reciverIsSet() =
//        order.reciverIsSet()
    fun addressIsValid() =
        order.addressIsValid()
    fun reciverIsValid() =
        order.reciverIsValid()

}
