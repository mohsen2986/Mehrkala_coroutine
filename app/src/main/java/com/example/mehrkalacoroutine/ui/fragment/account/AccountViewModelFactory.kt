package com.example.mehrkalacoroutine.ui.fragment.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mehrkalacoroutine.data.repository.UserInformationRepository
import com.example.mehrkalacoroutine.ui.fragment.mainPage.MainPageViewModel

class AccountViewModelFactory(
    private val userInformationRepository: UserInformationRepository
):ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AccountViewModel(userInformationRepository) as T
    }
}