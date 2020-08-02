package com.example.mehrkalacoroutine.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

const val USER_INFORMATION_ID = 0

@Entity(tableName = "user_information")
data class UserInformation(
    var username :String = "unknown",
    var password :String = "unknown",
    var phone :String = "1234" ,
    var token :String = "unknown" ,
    var logined :Boolean = false
){
    @PrimaryKey(autoGenerate = false)
    var id:Int =
        USER_INFORMATION_ID
}