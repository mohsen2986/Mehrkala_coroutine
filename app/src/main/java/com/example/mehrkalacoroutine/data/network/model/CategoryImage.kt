package com.example.mehrkalacoroutine.data.network.model

import com.google.gson.annotations.SerializedName

data class CategoryImage(
    val id :Int ,
    @SerializedName("name")
    val categoty :String ,
    @SerializedName("type")
    val category_id :String ,
    @SerializedName("img")
    val img :String
)