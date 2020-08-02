package com.example.mehrkalacoroutine.ui.fragment.ShowItemDetailsFragment

import androidx.lifecycle.ViewModel
import com.example.mehrkalacoroutine.data.repository.ItemRepository
import com.example.mehrkalacoroutine.ui.base.lazyDeferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
class ShowItemDetailsViewModel(
    private val itemRepository: ItemRepository
) : ViewModel() {
     fun addToBasket(id:Int) = GlobalScope.launch(IO) {
        itemRepository.addToBasket(id)
    }
    val categories by lazyDeferred{
        itemRepository.getCategories()
    }
}

