package com.example.mehrkalacoroutine.data.network.model

import com.example.mehrkala.model.Item
import com.example.mehrkala.model.MetaData
import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("datas")
    var datas:List<Category>,
    @SerializedName("metadata")
    var metaData: MetaData
)