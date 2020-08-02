package com.example.mehrkalacoroutine.ui.fragment.ShowItemDetailsFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mehrkalacoroutine.data.repository.ItemRepository

class ShowItemDetailsFragmentViewModelFactory(
    private val itemRepository: ItemRepository
): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShowItemDetailsViewModel(itemRepository) as T
    }
}