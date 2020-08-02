package com.example.mehrkala.model


import com.google.gson.annotations.SerializedName

data class BasketResponse(
    @SerializedName("datas")
    val datas: List<Data>,
    @SerializedName("metadata")
    val metadata: MetadataX
)