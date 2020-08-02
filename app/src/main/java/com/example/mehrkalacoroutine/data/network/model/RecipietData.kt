package com.example.mehrkalacoroutine.data.network.model

import com.google.gson.annotations.SerializedName

data class RecipietData(
    @SerializedName("receipt")
    val receipt:String ,
    @SerializedName("receipt_offer")
    val receiptOffer:String
)