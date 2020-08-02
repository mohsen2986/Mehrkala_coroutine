package com.example.mehrkalacoroutine.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mehrkalacoroutine.data.database.daos.UserInformationDao
import com.example.mehrkalacoroutine.data.database.model.UserInformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Database(
    entities = [UserInformation::class] ,
    version = 1
)
abstract class UserInformationDatabase:RoomDatabase(){
    abstract fun userInformationDao():UserInformationDao

    companion object{
        @Volatile private var instance: UserInformationDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }
        }
        private val callback:Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase)  {
                super.onCreate(db)
                populateFirstData()
            }
        }
        private fun populateFirstData() = GlobalScope.launch(IO){
                instance?.userInformationDao()?.upset(UserInformation())
        }
        private fun buildDatabase(context :Context) =
            Room.databaseBuilder(context ,
                UserInformationDatabase::class.java ,
                "forecast.db")
                .addCallback(callback)
                .build()
    }
}