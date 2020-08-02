package com.example.mehrkala.model

import com.google.gson.annotations.SerializedName

data class ItemsResponse(
        @SerializedName("datas")
        var datas:List<Item> ,
        @SerializedName("metadata")
        var metaData: MetaData
)