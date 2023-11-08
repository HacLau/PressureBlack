package com.testbird.pressureblack.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.testbird.pressureblack.adapter.RecordEntity

@Database(entities = [RecordEntity::class], version = 1,exportSchema = false)
abstract class RecordDatabaseManager : RoomDatabase() {
    abstract fun getHelper():DatabaseHelper

    companion object{
        private var instance:RecordDatabaseManager? = null
        fun getManager(context: Context):RecordDatabaseManager{
            return instance?: synchronized(this){
                Room.databaseBuilder(
                    context,RecordDatabaseManager::class.java,"record"
                ).build()
            }.let {
                instance = it
                instance!!
            }
        }
    }
}