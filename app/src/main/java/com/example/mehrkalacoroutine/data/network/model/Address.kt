package com.example.mehrkalacoroutine.data.network.model

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("id")
    val id:Int = 0,
    @SerializedName("address")
    val address :String = "unknown",
    @SerializedName("post_number")
    val postNumer :String = "unknown"
){
    var isSelected:Boolean = false
}