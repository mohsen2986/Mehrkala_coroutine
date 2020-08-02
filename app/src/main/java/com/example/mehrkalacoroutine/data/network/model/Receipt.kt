package com.example.mehrkalacoroutine.data.network.model

import com.example.mehrkala.model.Item
import com.example.mehrkala.model.MetaData
import com.google.gson.annotations.SerializedName

data class Receipt(
    @SerializedName("datas")
    val item:List<Item> ,
    @SerializedName("receipt")
    val receipt:RecipietData ,
    @SerializedName("metadata")
    val metadata:MetaData
)