package com.example.mehrkalacoroutine.ui.fragment.account

import androidx.lifecycle.ViewModel
import com.example.mehrkalacoroutine.data.database.model.UserInformation
import com.example.mehrkalacoroutine.data.repository.UserInformationRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class AccountViewModel(
    private val userInformationRepository: UserInformationRepository
) : ViewModel() {
    suspend fun userInformation():UserInformation?  =
            userInformationRepository.getUserInformation()

    suspend fun updateUserInformation(username:String , password:String , phone:String , email: String) :Boolean=
        userInformationRepository.updateUserInformation(username, password, phone , email)

    suspend fun logOut() =
        userInformationRepository.logOut()
}
