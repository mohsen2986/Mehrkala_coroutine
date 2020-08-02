package com.example.mehrkala.model

import com.google.gson.annotations.SerializedName

data class Boarder(
        @SerializedName("id")
        var id:Int ,
        @SerializedName("title")
        var title:String,
        @SerializedName("image")
        var image:String )