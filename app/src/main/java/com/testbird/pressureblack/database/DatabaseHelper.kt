package com.testbird.pressureblack.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.testbird.pressureblack.adapter.RecordEntity

@Dao
interface DatabaseHelper{
    @Insert
    fun insert(recordEntity: RecordEntity)

    @Update
    fun update(recordEntity: RecordEntity)

    @Query("select * from recordentity order by time desc")
    fun query():List<RecordEntity>
}