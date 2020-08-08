package com.example.mehrkalacoroutine.data.network.model

import com.example.mehrkala.model.MetaData
import com.google.gson.annotations.SerializedName

data class OrdersHistoryResponse(
    @SerializedName("datas")
    val datas :List<OrdersHistory> ,
    @SerializedName("metadata")
    val metadata: MetaData
)