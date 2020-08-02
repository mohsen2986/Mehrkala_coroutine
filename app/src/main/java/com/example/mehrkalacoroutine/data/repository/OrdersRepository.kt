package com.example.mehrkalacoroutine.data.repository

import com.example.mehrkalacoroutine.data.network.api.ApiInterface

class OrdersRepository(
    private val api: ApiInterface
){
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
}