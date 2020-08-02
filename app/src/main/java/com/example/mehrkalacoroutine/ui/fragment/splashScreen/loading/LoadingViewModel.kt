package com.example.mehrkalacoroutine.ui.fragment.splashScreen.loading

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.mehrkalacoroutine.data.network.api.ApiInterface
import com.example.mehrkalacoroutine.data.repository.UserInformationRepository
import com.example.mehrkalacoroutine.ui.base.lazyDeferred

class LoadingViewModel(
    private val repository: UserInformationRepository
) : ViewModel() {
    val userInformationRepository by lazyDeferred{
        repository.userInformation()
    }
//    suspend fun isLogin():Boolean{
//        if (repository.userInformation() == null) return false
//        return repository.userInformation()?.logined!!
//    }

    suspend fun isLogin() :Pair<Boolean , Boolean> =
        repository.isLogin()
    val loginState by lazyDeferred{
        repository.isLogin()
    }
}
