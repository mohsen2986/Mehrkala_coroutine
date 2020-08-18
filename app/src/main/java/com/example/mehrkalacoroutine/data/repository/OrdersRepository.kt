package com.example.mehrkalacoroutine.data.repository

import com.example.mehrkalacoroutine.data.network.api.ApiInterface
import com.example.mehrkalacoroutine.data.network.model.Address
import com.example.mehrkalacoroutine.data.network.model.ReciverInformation

class OrdersRepository(
    private val api: ApiInterface
){
    lateinit var address: Address
    lateinit var reciver: ReciverInformation

    suspend fun sendPaymentInfo(refId:String) =
            sendPayment(refId)
    suspend fun getAddress()=
        api.getAddress()
    suspend fun getReciverInfo() =
        api.getInfo()
    suspend fun addAddress(address:String , postNumber:String) =
        api.addAddress(address , postNumber)
    suspend fun addInfo(name:String , number:String) =
        api.addInfo(name , number)
    suspend fun getReceipt() =
        api.getReceipt()
    private suspend fun sendPayment(refId:String ) =
        api.sendPaymentInformation(addressId = address.id.toString() , reciverId = reciver.id.toString() , refId = refId)
    fun addressIsSet() =
        this::address.isInitialized
    fun reciverIsSet() =
        this::reciver.isInitialized

    fun addressIsValid(): Boolean {
        return addressIsSet() && address != Address()
    }
    fun reciverIsValid(): Boolean {
        return reciverIsSet() && reciver != ReciverInformation()
    }
}