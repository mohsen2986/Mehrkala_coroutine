package com.example.mehrkalacoroutine.ui.fragment.mainPage

import androidx.lifecycle.ViewModel
import com.example.mehrkala.model.Item
import com.example.mehrkalacoroutine.data.repository.ItemRepository
import com.example.mehrkalacoroutine.data.repository.OrdersRepository
import com.example.mehrkalacoroutine.ui.base.lazyDeferred
import com.haroldadmin.cnradapter.NetworkResponse

class  MainPageViewModel(
    private val itemRepository: ItemRepository
) : ViewModel() {
    val boarderItems by lazyDeferred{
        itemRepository.getBoarders()
    }
    val topSales by lazyDeferred{
        itemRepository.getTopSapesItems()
    }
    val newItems by lazyDeferred{
        itemRepository.getNewItems()
    }
    val category by lazyDeferred{
        itemRepository.getImageCategories()
    }
    val offers  by lazyDeferred{
        itemRepository.getOffers()
    }
}
