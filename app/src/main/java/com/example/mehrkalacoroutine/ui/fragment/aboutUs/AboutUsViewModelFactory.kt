package com.example.mehrkalacoroutine.ui.fragment.aboutUs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AboutUsViewModelFactory(
    ): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AboutUsFragmentViewModel() as T
    }
}