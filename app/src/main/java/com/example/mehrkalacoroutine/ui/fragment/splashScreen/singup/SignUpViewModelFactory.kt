package com.example.mehrkalacoroutine.ui.fragment.splashScreen.singup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mehrkalacoroutine.data.network.api.ApiInterface
import com.example.mehrkalacoroutine.data.repository.UserInformationRepository

class SignUpViewModelFactory (
    private val userInformationRepository: UserInformationRepository
):ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignupViewModel(userInformationRepository) as T
    }
}