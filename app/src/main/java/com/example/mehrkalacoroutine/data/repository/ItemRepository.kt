package com.example.mehrkalacoroutine.data.repository

import android.util.Log
import com.example.mehrkala.model.Boarder
import com.example.mehrkala.model.Item
import com.example.mehrkala.model.MainPageResponse
import com.example.mehrkala.model.MetaData
import com.example.mehrkalacoroutine.data.network.api.ApiInterface
import com.example.mehrkalacoroutine.data.network.model.Category
import com.example.mehrkalacoroutine.data.network.model.CategoryImage
import com.haroldadmin.cnradapter.NetworkResponse

class   ItemRepository (
    private val apiInterface: ApiInterface
){

    suspend fun getItems(page:Int , query:String)  =
        apiInterface.getItemsPerPage(page , query)

    suspend fun getPages(query:String):Int {
        val result = apiInterface.getItemsPerPage(0 , query)
        when(result){
            is NetworkResponse.Success -> return result.body.metaData.pages
        }
        return  0
    }

    suspend fun getNewItems(): List<Item> {
        val response = apiInterface.getNewItems()
        when(response){
            is NetworkResponse.Success -> return response.body.datas
        }
        return emptyList()
    }
    suspend fun getTopSapesItems(): List<Item> {
        val response = apiInterface.getTopSales()
        when(response) {
            is NetworkResponse.Success -> return response.body.datas
        }
        return emptyList()
    }

    suspend fun getBoarders(): List<Boarder> {
        val response = apiInterface.getNewItems()
        when(response){
            is NetworkResponse.Success -> return response.body.boarders
        }
        return emptyList()
    }

    // Basket Items
    suspend fun addToBasket(id:Int): MetaData {
        val result = apiInterface.basket(id = id , query = "addToBasket")
        when(result){
            is NetworkResponse.Success -> return result.body
        }
        return MetaData("err" , "101" ,0)
    }

    suspend fun plusItemCount(id:Int): MetaData {
        val result = apiInterface.basket(id = id , query = "addItemCount")
        when(result){
            is NetworkResponse.Success -> return result.body
        }
        return MetaData("err" , "101" ,0)
    }

    suspend fun minusItemCount(id:Int): MetaData {
        val result = apiInterface.basket(id = id, query = "minusItemCount")
        when(result){
            is NetworkResponse.Success -> return result.body
        }
        return MetaData("err" , "101" ,0)
    }

    suspend fun deleteFromBasket(id:Int): MetaData {
        val result = apiInterface.basket(id = id , query = "delete")
        when(result){
            is NetworkResponse.Success -> return result.body
        }
        return MetaData("err" , "101" ,0)
    }
    suspend fun visitItem(id:Int) =
        apiInterface.visitItem(id)

    suspend fun getCategories(): List<Category> {
        val result = apiInterface.getCategory()
        when(result){
            is NetworkResponse.Success -> return result.body.datas
        }
        return emptyList()
    }
    suspend fun getImageCategories(): List<CategoryImage> {
        val result = apiInterface.getImageCategories()
        when(result){
            is NetworkResponse.Success -> return result.body.datas
        }
        return emptyList()
    }


}