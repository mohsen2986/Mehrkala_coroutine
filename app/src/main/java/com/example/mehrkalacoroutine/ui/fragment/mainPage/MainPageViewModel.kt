package com.example.mehrkalacoroutine.ui.fragment.mainPage

import androidx.lifecycle.ViewModel
import com.example.mehrkalacoroutine.data.repository.ItemRepository
import com.example.mehrkalacoroutine.data.repository.OrdersRepository
import com.example.mehrkalacoroutine.ui.base.lazyDeferred

class MainPageViewModel(
    private val itemRepository: ItemRepository ,
    private val ordersRepository: OrdersRepository
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

}
