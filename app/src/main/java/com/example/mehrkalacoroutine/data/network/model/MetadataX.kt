package com.example.mehrkala.model


import com.google.gson.annotations.SerializedName

data class MetadataX(
    val code: String, // 105
    @SerializedName("status")
    val status: String // ok
)