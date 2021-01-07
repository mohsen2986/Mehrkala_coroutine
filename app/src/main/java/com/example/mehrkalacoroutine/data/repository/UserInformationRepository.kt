package com.example.mehrkalacoroutine.data.repository

import android.util.Log
import com.example.mehrkalacoroutine.data.database.daos.UserInformationDao
import com.example.mehrkalacoroutine.data.database.model.UserInformation
import com.example.mehrkalacoroutine.data.network.api.ApiInterface
import com.example.mehrkalacoroutine.data.network.model.RequestInformation
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserInformationRepository(
    private val userInformationDao:UserInformationDao ,
    private val apiInterface:ApiInterface
){
    private fun insertData(userInformation: UserInformation){
        GlobalScope.launch(IO) {
            userInformationDao.upset(userInformation)
        }
    }
    suspend fun getUserInformation(): UserInformation? =
        userInformationDao.getUserInformation()

    suspend fun userInformation() : UserInformation? = withContext(IO) {
        return@withContext getUserInformation()
    }

    // signUp
     suspend fun signUp(username:String , password:String , phone: String , email: String): NetworkResponse<RequestInformation, RequestInformation> {
        val callBack = sendSignUpData(username , password, phone , email)
        when(callBack){
            is NetworkResponse.Success ->{
                if(callBack.body.code.equals("105"))
                    insertData(UserInformation(username , password , phone , callBack.body.token , true))
            }
        }
//        if (callBack..code.equals("105")){
//                insertData(UserInformation(username , password , phone , callBack.token , true))
//                return true
//        }
//        return false
        return callBack
    }
    private suspend fun sendSignUpData(username:String , password: String , phone: String , email: String) : NetworkResponse<RequestInformation, RequestInformation> =
        withContext(IO){
            return@withContext apiInterface.signUp(username , password , phone , email)
        }
    // login
    suspend fun login(username:String , password:String):NetworkResponse<RequestInformation, RequestInformation> {
        val callBack = sendLoginData(username , password)
        when (callBack) {
            is NetworkResponse.Success -> {
                if (callBack.body.code.equals("105"))
                insertData(UserInformation(username , password , callBack.body.phone , callBack.body.token , true , callBack.body.email))
            }
        }
        return callBack
    }
    suspend fun isLogin(): Pair<Boolean , Boolean> {
        return withContext(IO) {
            val userInformation = userInformation()
            if (userInformation?.logined!!) {
                when (val callback = login(userInformation.username, userInformation.password)) {
                    is NetworkResponse.Success -> {
                        if (callback.body.code == "105") return@withContext Pair(first = true,second = true)
                    }
                    else ->
                            return@withContext Pair(first = true , second = false)
                }
            }
            return@withContext Pair(first = false , second = false)
        }
    }
    private suspend fun sendLoginData(username:String , password:String) : NetworkResponse<RequestInformation, RequestInformation> =
        withContext(IO){
            return@withContext apiInterface.login(username , password,"login")
        }
    // update userInfo
    suspend fun updateUserInformation(username:String , password:String , phone:String , email: String):Boolean{
        return when(val callBack = sendUpdateUser(username , password , phone , email)) {
            is NetworkResponse.Success -> {
                if (callBack.body.code.equals("105")) {
                    val user = UserInformation(username, password, phone, callBack.body.token, true, email)
                    insertData(user)
                    true
                } else
                    false
            }
            else -> false
        }
    }
    private suspend fun sendUpdateUser(username:String , password:String , phone:String , email: String) : NetworkResponse<RequestInformation, RequestInformation> =
        withContext(IO){
            return@withContext apiInterface.updateAccount(username , password , phone , email)
        }
    // logOut
    suspend fun logOut(){
        insertData(UserInformation())
    }
}

