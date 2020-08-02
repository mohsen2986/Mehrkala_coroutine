package com.example.mehrkalacoroutine.ui.fragment.splashScreen.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mehrkalacoroutine.data.repository.UserInformationRepository

class LoadingViewModelFactory(
    private val userInformationRepository: UserInformationRepository
):ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoadingViewModel(userInformationRepository) as T
    }
}