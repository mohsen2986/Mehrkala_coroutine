package com.example.mehrkalacoroutine.data.dataSource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.mehrkala.model.Item
import com.example.mehrkala.model.ItemsResponse
import com.example.mehrkalacoroutine.data.network.NetworkState
import com.example.mehrkalacoroutine.data.repository.ItemRepository
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.*

class ItemDataSource<T>(
    private val repository: ItemRepository ,
    private val query:String ,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, T>() {
    // FOR DATA---
    private val supervisorJob = SupervisorJob()
    private val networkState = MutableLiveData<NetworkState>()
    private var retryQuery: (() -> Any)? = null // Keep reference of the last query (to be able to retry it if necessary)
    private var pages :Int ?= null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        retryQuery = { loadInitial(params, callback) }
        loadInitial {
            callback.onResult(it , null , if(pages == 0) null else 1)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        val page = params.key
        retryQuery = {loadAfter(params , callback)}
        executeQuery(page){
            callback.onResult( it , if(page == pages ) null else page+1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {}
    // UTILS--
    private fun executeQuery(page:Int , callBack:(List<T>) -> Unit ){
        networkState.postValue(NetworkState.RUNNING)
        scope.launch (getJobErrorHandler() + supervisorJob){
            val request = repository.getItems(page , query)
            retryQuery = null
            networkState.postValue(NetworkState.SUCCESS)
            when(request){
                is NetworkResponse.Success ->
                    callBack(request.body.datas as List<T>)
            }
        }
    }
    private fun loadInitial(callBack:(List<T>) -> Unit ){
        scope.launch (getJobErrorHandler() + supervisorJob){
            pages = repository.getPages(query)
            executeQuery(0) {
                callBack(it)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Log.e(ItemDataSource::class.java.simpleName, "An error happened: $e")
        networkState.postValue(NetworkState.FAILED)
    }

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren()   // Cancel possible running job to only keep last result searched by user
    }

    // PUBLIC API ---

    fun getNetworkState(): LiveData<NetworkState> =
        networkState

    fun refresh() =
        this.invalidate()


}