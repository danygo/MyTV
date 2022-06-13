package com.danielsenik.mytv.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danielsenik.mytv.model.Show

@Dao
interface ShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShows(shows: List<Show>)

    @Query("SELECT * FROM shows")
    fun retrieveShows(): LiveData<List<Show>>

    @Query("DELETE FROM shows")
    fun deleteShows()
}