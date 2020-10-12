package com.bmisr.theair.di

import android.content.Context
import androidx.room.Room
import com.bmisr.theair.db.TVShowsDao
import com.bmisr.theair.db.TVShowsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * Created by Mohamed Salama on 10/12/2020.
 */

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): TVShowsDatabase {
        return  return Room.databaseBuilder(
            appContext,
            TVShowsDatabase::class.java,
            "tv_shows.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideTVShowsDao(db: TVShowsDatabase): TVShowsDao {
        return db.tvShowsDao()
    }

}