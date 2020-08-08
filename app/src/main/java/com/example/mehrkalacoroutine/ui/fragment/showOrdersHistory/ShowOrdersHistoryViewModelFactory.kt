package com.example.mehrkalacoroutine.ui.fragment.showOrdersHistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mehrkalacoroutine.data.repository.ItemRepository

class ShowOrdersHistoryViewModelFactory(
    private val itemsRepository: ItemRepository
): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShowOrdersHistoryViewModel(itemsRepository) as T
    }
}