package com.example.mehrkalacoroutine.data.network

import com.example.mehrkalacoroutine.data.network.api.ApiInterface
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Service(
    private val client:OkHttpClient
){
    operator fun invoke() :ApiInterface{
        return Retrofit.Builder()
            .baseUrl("https://www.paarandco.ir/mehrkala/api/ver1/")
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiInterface::class.java)
    }
}