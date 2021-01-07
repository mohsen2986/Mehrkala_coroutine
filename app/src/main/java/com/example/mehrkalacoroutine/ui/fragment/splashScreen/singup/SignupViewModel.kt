package com.example.mehrkalacoroutine.ui.fragment.splashScreen.singup

import androidx.lifecycle.ViewModel
import com.example.mehrkalacoroutine.data.database.model.UserInformation
import com.example.mehrkalacoroutine.data.network.api.ApiInterface
import com.example.mehrkalacoroutine.data.network.model.RequestInformation
import com.example.mehrkalacoroutine.data.repository.UserInformationRepository
import com.example.mehrkalacoroutine.ui.base.lazyDeferred
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class SignupViewModel(
    private val repository: UserInformationRepository
) : ViewModel() {
    suspend fun signUp(username:String , password:String , phone:String , email: String ): NetworkResponse<RequestInformation, RequestInformation> =
        repository.signUp(username , password , phone , email)

}
