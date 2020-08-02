package com.example.mehrkalacoroutine.data.network.model

import com.example.mehrkala.model.MetaData
import com.google.gson.annotations.SerializedName

data class CategoryImageResponse(
    @SerializedName("datas")
    var datas:List<CategoryImage>

)