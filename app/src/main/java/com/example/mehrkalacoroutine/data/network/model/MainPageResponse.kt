package com.example.mehrkala.model

import com.example.mehrkalacoroutine.data.network.model.RequestInformation
import com.google.gson.annotations.SerializedName

data class MainPageResponse(
        @SerializedName("data")
        var datas:List<Item>? ,
        @SerializedName("boarders")
        var boarders:List<Boarder>? ,
        @SerializedName("metadata")
        var MetaData: RequestInformation?
)