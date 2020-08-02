package com.example.mehrkalacoroutine.ui.fragment.showItems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mehrkalacoroutine.data.repository.ItemRepository
import com.example.mehrkalacoroutine.data.repository.UserInformationRepository

class ShowItemsViewModleFactory(
    private val itemRepository: ItemRepository ,
    private val userInformationRepository: UserInformationRepository
): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShowitemsViewModel(itemRepository , userInformationRepository) as T
    }
}