package com.example.mehrkala.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: String, // 2
    @SerializedName("product_id")
    val productId: String, // 3322
    @SerializedName("username")
    val username: String // mohsen
)