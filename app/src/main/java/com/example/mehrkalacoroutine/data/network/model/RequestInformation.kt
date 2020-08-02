package com.example.mehrkalacoroutine.data.network.model

import com.google.gson.annotations.SerializedName

data class RequestInformation (
        @SerializedName("status")
        var result :String,
        @SerializedName("code")
        var code :String ,
        @SerializedName("token")
        var token :String ,
        @SerializedName("phone")
        var phone:String
)

