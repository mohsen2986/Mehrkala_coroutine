package com.example.mehrkalacoroutine.ui.fragment.showOrdersHistory

import androidx.lifecycle.ViewModel
import com.example.mehrkalacoroutine.data.repository.ItemRepository
import com.example.mehrkalacoroutine.ui.base.lazyDeferred

class ShowOrdersHistoryViewModel(
    private val itemsRepository: ItemRepository
    ): ViewModel() {

    suspend fun getOrderItems(paymentId:String) =
        itemsRepository.getOrderItems(paymentId)
}
