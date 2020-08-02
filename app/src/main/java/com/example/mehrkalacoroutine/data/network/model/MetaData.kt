package com.example.mehrkala.model

import com.google.gson.annotations.SerializedName

data class MetaData(
        @SerializedName("status")
        var status :String ,
        @SerializedName("code")
        var code :String ,
        @SerializedName("pages")
        var pages :Int
)
