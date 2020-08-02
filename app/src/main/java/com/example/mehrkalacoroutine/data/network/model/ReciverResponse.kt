package com.example.mehrkalacoroutine.data.network.model

import com.example.mehrkala.model.MetaData
import com.google.gson.annotations.SerializedName

data class ReciverResponse(
    @SerializedName("datas")
    val data:List<ReciverInformation> ,
    @SerializedName("metadata")
    val metadata: MetaData
)