package com.example.mehrkalacoroutine.ui.fragment.splashScreen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mehrkalacoroutine.data.network.api.ApiInterface
import com.example.mehrkalacoroutine.data.repository.UserInformationRepository

class LoginViewModelFactory(
    private val userInformationRepository: UserInformationRepository
):ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(userInformationRepository) as T
    }
}