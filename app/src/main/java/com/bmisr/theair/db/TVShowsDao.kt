package com.bmisr.theair.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bmisr.theair.api.response.TVShow


@Dao
interface TVShowsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVShows(tvShows: List<TVShow>)

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insert(tvShow: TVShow?)

    @Query("SELECT * from tv_shows_table where id = :tvShowId ")
    fun loadTVShowById(tvShowId: Int): LiveData<TVShow?>

    @Query("SELECT * from tv_shows_table")
    fun loadTVShows(): LiveData<List<TVShow>>

    @Query("DELETE FROM tv_shows_table where id = :tvShowId")
    suspend fun deleteTVShow(tvShowId: Int?)
}