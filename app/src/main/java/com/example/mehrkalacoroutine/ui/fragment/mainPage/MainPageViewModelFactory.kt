package com.example.mehrkalacoroutine.ui.fragment.mainPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mehrkalacoroutine.data.repository.ItemRepository
import com.example.mehrkalacoroutine.data.repository.OrdersRepository

class MainPageViewModelFactory(
    private val itemRepository:ItemRepository ,
    private val ordersRepository: OrdersRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainPageViewModel(itemRepository , ordersRepository) as T
    }
}