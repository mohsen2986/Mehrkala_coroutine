package com.example.mehrkalacoroutine.data.network.model

import com.google.gson.annotations.SerializedName

data class OrdersHistory(
    @SerializedName("id")
    val id:String ,
    @SerializedName("cost")
    val cost:String ,
    @SerializedName("offer_cost")
    val OfferCost:String ,
    @SerializedName("is_send")
    val isSend:String ,
    @SerializedName("address")
    val address:String ,
    @SerializedName("post_number")
    val postNumber:String ,
    @SerializedName("name")
    val name:String ,
    @SerializedName("phone")
    val phone:String ,
    @SerializedName("basket_name")
    val basketName:String ,
    @SerializedName("date_time")
    val dateTime :String ,
    @SerializedName("pdf")
    val pdf :String
)