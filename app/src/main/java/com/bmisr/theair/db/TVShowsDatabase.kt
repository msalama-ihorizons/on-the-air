package com.bmisr.theair.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bmisr.theair.api.TVShow
import com.tiendito.bmisrmovies.db.TVShowsDao

/**
 * Created by Mohamed Salama on 9/6/2020.
 */

@Database(entities = [TVShow::class], version = 3, exportSchema = false)
@TypeConverters(DataConverters::class)
abstract class TVShowsDatabase: RoomDatabase() {
    abstract fun tvShowsDao(): TVShowsDao
}