package com.example.mehrkalacoroutine.data.repository

import com.example.mehrkalacoroutine.data.database.daos.UserInformationDao
import com.example.mehrkalacoroutine.ui.base.lazyDeferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class TokenRepository(
    private val userInformationDao: UserInformationDao
){
    val token by lazyDeferred{
        userInformationDao.getUserInformation()
    }
    fun token():String = runBlocking(IO){
        if(userInformationDao.getUserInformation() != null)
            return@runBlocking userInformationDao.getUserInformation().token
        return@runBlocking  ""
    }
}