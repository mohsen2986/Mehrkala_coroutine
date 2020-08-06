package com.example.mehrkalacoroutine.data.network.api

import com.example.mehrkala.model.*
import com.example.mehrkalacoroutine.data.network.model.*
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface{
    // Login
    @POST("login.php")
    suspend fun login(
        @Query("username") username: String ,
        @Query("password") password: String ,
        @Query("type") type:String
    ): NetworkResponse <RequestInformation , RequestInformation>

    // signup
    @POST("signUp.php")
     suspend fun signUp(
               @Query("username") username: String,
               @Query("password") password: String,
               @Query("phone") phone: String
    ): NetworkResponse<RequestInformation , RequestInformation>

    // get paged items
    @POST("getProducts.php")
    suspend fun getItemsPerPage(
        @Query("page") page: Int ,
        @Query("query") query: String
    ): NetworkResponse<ItemsResponse , ItemsResponse>
    // get OFFERS
    @POST("getProducts.php")
    suspend fun getOffers(
        @Query("page") page: Int ,
        @Query("query") query: String
    ): NetworkResponse<ItemsResponse , ItemsResponse>

    // get TopSales
    @POST("getTopSales.php")
    suspend fun getTopSales(
    ): NetworkResponse<MainPageResponse , MainPageResponse>

    // get NewItems
    @POST("getNewItems.php")
    suspend fun getNewItems(
    ): NetworkResponse<MainPageResponse , MainPageResponse>

    // get itemData
    @POST("getItemData.php")
    fun getItemData(
        @Query("id") id: String
    ): Deferred<ItemsResponse>

    // basket Items
    @POST("getBasket.php")
    fun getBasketData(
        @Query("token") username: String
    ): Deferred<BasketResponse>

    //
    @POST("basket.php")
    suspend fun basket(
        @Query("id") id: Int ,
        @Query("query") query: String
    ): NetworkResponse< MetaData , MetaData>

    // update account information
    @POST("updateUserInfo.php")
    suspend fun updateAccount(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("phone") phone:String
    ): NetworkResponse <RequestInformation , RequestInformation>

    // category
    @POST("category.php")
    suspend fun getCategory(): NetworkResponse<CategoryResponse , CategoryResponse>
    // Category with Image
    @POST("category.php")
    suspend fun getImageCategories() : NetworkResponse<CategoryImageResponse ,  CategoryImageResponse>

    @POST("visitItem.php")
    suspend fun visitItem(
        @Query("id") id:Int
    ): NetworkResponse<Unit , Unit>

    @POST("ReciversInformation.php")
    suspend fun getAddress(
        @Query("query") query:String = "getAddress"
    ): NetworkResponse<AddressResponse , AddressResponse>

    @POST("ReciversInformation.php")
    suspend fun getInfo(
        @Query("query") query: String = "getInfo"
    ): NetworkResponse<ReciverResponse , ReciverResponse>

    @POST("ReciversInformation.php")
    suspend fun addAddress(
        @Query("address") address:String ,
        @Query("post_number") postNumber:String ,
        @Query("query") query:String = "addAddress"
    ): NetworkResponse<StateRequest , StateRequest>

    @POST("ReciversInformation.php")
    suspend fun addInfo(
        @Query("name") name:String ,
        @Query("number") number:String ,
        @Query("query") query: String = "addInfo"
    ): NetworkResponse<StateRequest , StateRequest>

    @POST("getReceipt.php")
    suspend fun getReceipt(
    ): NetworkResponse<Receipt , Receipt>

    @POST("payment.php")
    suspend fun sendPaymentInformation(
        @Query("address_id") addressId:String ,
        @Query("reciver_id") reciverId:String ,
        @Query("ref_id") refId:String
    ): NetworkResponse< Receipt , Receipt>
}