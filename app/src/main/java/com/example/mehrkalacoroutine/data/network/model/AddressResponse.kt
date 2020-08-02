package com.example.mehrkalacoroutine.data.network.model

import com.example.mehrkala.model.MetaData
import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("datas")
    val address: List<Address> ,
    @SerializedName("metadata")
    val metaData: MetaData
)