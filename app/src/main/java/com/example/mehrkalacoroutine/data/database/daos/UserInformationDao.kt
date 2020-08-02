package com.example.mehrkalacoroutine.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.mehrkalacoroutine.data.database.model.USER_INFORMATION_ID
import com.example.mehrkalacoroutine.data.database.model.UserInformation
import kotlinx.coroutines.Deferred

@Dao
interface UserInformationDao{
    // insert and update information
    @Insert(onConflict = REPLACE)
    suspend fun upset(userInformation: UserInformation)
    // getUserInformation
    @Query("SELECT * FROM user_information ")
    suspend fun getUserInformation(): UserInformation
}