package com.example.mehrkalacoroutine.data.dataSource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mehrkala.model.Item
import com.example.mehrkalacoroutine.data.repository.ItemRepository
import kotlinx.coroutines.CoroutineScope

class ItemDataSourceFactory<T>(
    private val repository: ItemRepository ,
    private var query: String ,
    private val scope: CoroutineScope
):DataSource.Factory<Int , T>(){
    val source = MutableLiveData<ItemDataSource<T>>()

    override fun create(): DataSource<Int, T> {
        val source = ItemDataSource<T>(repository , query, scope)
        this.source.postValue(source)
        return source
    }

    // --- PUBLIC API
    fun getSource() = source.value
    fun getQuery() = query

    fun updateQuery(query:String){
        this.query = query
        getSource()?.refresh()
    }

}