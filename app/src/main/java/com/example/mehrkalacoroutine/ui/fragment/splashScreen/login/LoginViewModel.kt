package com.example.mehrkalacoroutine.ui.fragment.splashScreen.login

import androidx.lifecycle.ViewModel
import com.example.mehrkalacoroutine.data.network.model.RequestInformation
import com.example.mehrkalacoroutine.data.repository.UserInformationRepository
import com.haroldadmin.cnradapter.NetworkResponse

class LoginViewModel (
    private val repository: UserInformationRepository
) : ViewModel() {
    suspend fun login(username:String , password:String): NetworkResponse<RequestInformation, RequestInformation> =
        repository.login(username , password)
}
