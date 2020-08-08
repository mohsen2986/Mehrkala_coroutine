package com.example.mehrkala.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
        @SerializedName("id")
        var id :Int ,
        @SerializedName("name")
        var name :String ,
        @SerializedName("description")
        var description :String ,
        @SerializedName("price")
        var price :String ,
        @SerializedName("type")
        var type :String ,
        @SerializedName("offer")
        var offer :String ,
        @SerializedName("img")
        var image :String ,
        @SerializedName("img2")
        var imageTwo:String,
        @SerializedName("img3")
        var imageThree:String,
        @SerializedName("user_add_id")
        var owener :String ,
        @SerializedName("offer_price")
        var offerPrice :String ,
        @SerializedName("sales")
        var sales:String ,
        @SerializedName("count")
        var count:Int ,
        @SerializedName("visitors")
        var visitors:String
): Parcelable , BaseObservable() {
        @Bindable
        fun getCountItem():Int = count
        fun setCountItem(count:Int){
                this.count = count
                notifyPropertyChanged(BR.countItem)
        }
}
