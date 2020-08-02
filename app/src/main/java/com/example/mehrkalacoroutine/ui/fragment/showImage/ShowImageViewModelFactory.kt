package com.example.mehrkalacoroutine.ui.fragment.showImage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShowImageViewModelFactory(
): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShowImageViewModel() as T
    }

}