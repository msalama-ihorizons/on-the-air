package com.bmisr.theair.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bmisr.theair.api.TVShow

/**
 * Created by Mohamed Salama on 10/12/2020.
 */

@Database(entities = [TVShow::class], version = 3, exportSchema = false)
@TypeConverters(DataConverters::class)
abstract class TVShowsDatabase: RoomDatabase() {
    abstract fun tvShowsDao(): TVShowsDao
}