package com.example.mehrkalacoroutine.data.network.model

import com.google.gson.annotations.SerializedName

class ReciverInformation(
    @SerializedName("id")
    val id :Int =0 ,
    @SerializedName("name")
    val name :String = "unknown",
    @SerializedName("phone")
    val phone :String = "unknown"
){
    var isSelected:Boolean = false
}