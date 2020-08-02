package com.example.mehrkalacoroutine.ui.fragment.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mehrkalacoroutine.data.repository.OrdersRepository

class BillsViewModelFactory(
    private val ordersRepository:OrdersRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BillsViewModel(ordersRepository) as T
    }
}