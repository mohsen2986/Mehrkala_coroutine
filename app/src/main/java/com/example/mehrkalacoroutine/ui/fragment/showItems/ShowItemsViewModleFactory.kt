package com.example.mehrkalacoroutine.ui.fragment.showItems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mehrkalacoroutine.data.repository.ItemRepository
import com.example.mehrkalacoroutine.data.repository.OrdersRepository
import com.example.mehrkalacoroutine.data.repository.UserInformationRepository

class ShowItemsViewModleFactory(
    private val itemRepository: ItemRepository ,
    private val userInformationRepository: UserInformationRepository ,
    private val ordersRepository: OrdersRepository
): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShowitemsViewModel(itemRepository , userInformationRepository , ordersRepository) as T
    }
}