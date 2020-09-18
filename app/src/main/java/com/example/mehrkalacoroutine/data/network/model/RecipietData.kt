package com.example.mehrkalacoroutine.data.network.model

import com.google.gson.annotations.SerializedName

data class RecipietData(
    @SerializedName("receipt")
    val receipt:String ,
    @SerializedName("offer")
    val offer:String ,
    @SerializedName("receipt_offer")
    val receiptOffer:String ,
    @SerializedName("post_cost")
    val postCost: String ,
    @SerializedName("taxation")
    val tax: String ,
    @SerializedName("total")
    val totalCost: String
)