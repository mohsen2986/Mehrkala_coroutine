package com.example.mehrkalacoroutine.data.network.model

import com.google.gson.annotations.SerializedName

data class CheckResponse(
    @SerializedName("code")
    val code: String ,
    @SerializedName("massage")
    val massage: String
)