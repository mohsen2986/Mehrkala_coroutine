package com.example.mehrkalacoroutine.ui.fragment.showItems

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mehrkala.model.Item
import com.example.mehrkalacoroutine.data.dataSource.ItemDataSourceFactory
import com.example.mehrkalacoroutine.data.network.NetworkState
import com.example.mehrkalacoroutine.data.repository.ItemRepository
import com.example.mehrkalacoroutine.data.repository.UserInformationRepository
import com.example.mehrkalacoroutine.ui.base.lazyDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class ShowitemsViewModel(
    private val itemRepository: ItemRepository ,
    private val userInformationRepository: UserInformationRepository
) : ViewModel() {
    // User Information
    val url by lazyDeferred{
        "http://www.paarandco.ir/mehrkala/basket.php?token=" + userInformationRepository.getUserInformation()?.token
    }
    // DATA
    protected val ioScope = CoroutineScope(Dispatchers.IO)

    private val itemDataSource = ItemDataSourceFactory<Item>(repository = itemRepository , query = "", scope = ioScope)

    val categories by lazyDeferred {
        itemRepository.getCategories()
    }
    // OBSERVABLES
    val users = LivePagedListBuilder(itemDataSource ,pagedListConfig()).build()
    val networkState : LiveData<NetworkState>? =
        Transformations.switchMap(itemDataSource.source) { it.getNetworkState() }


    fun fetchQuery(query:String){
        if(itemDataSource.getQuery() == query) return
        itemDataSource.updateQuery(query = query)
    }
    fun getQuery() = itemDataSource.getQuery()
    // PUBLIC API
    suspend fun addItemCount(id:Int) =
        itemRepository.plusItemCount(id)
    suspend fun minusItemCount(id:Int) =
        itemRepository.minusItemCount(id)
    suspend fun deleteItem(id:Int) =
        itemRepository.deleteFromBasket(id)
    suspend fun visitItem(id:Int) =
        itemRepository.visitItem(id)
    // refresh all list after an issue
    fun refreshAllList() = itemDataSource.getSource()?.refresh()

    // UTILS
    private fun pagedListConfig() =
        PagedList.Config.Builder()
            .setInitialLoadSizeHint(5)
            .setEnablePlaceholders(false)
            .setPageSize(5)
            .build()

    override fun onCleared() {
        super.onCleared()
        ioScope.coroutineContext.cancel()
    }
}
