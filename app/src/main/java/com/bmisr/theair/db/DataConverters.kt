package com.bmisr.theair.db

import androidx.room.TypeConverter
import com.bmisr.theair.api.response.Genre
import com.bmisr.theair.api.response.Network
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverters {

    @TypeConverter
    fun fromListToString(stringList: List<String?>?): String? {
        val type = object : TypeToken<List<String?>?>() {}.type
        return Gson().toJson(stringList, type)
    }

    @TypeConverter
    fun fromListToGenres(stringList: List<Genre?>?): String? {
        val type = object : TypeToken<List<Genre?>?>() {}.type
        return Gson().toJson(stringList, type)
    }

    @TypeConverter
    fun fromListToNetworks(stringList: List<Network?>?): String? {
        val type = object : TypeToken<List<Network?>?>() {}.type
        return Gson().toJson(stringList, type)
    }

    @TypeConverter
    fun toStringList(str: String?): List<String>? {
        val type = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson<List<String>>(str, type)
    }

    @TypeConverter
    fun toGenresList(str: String?): List<Genre>? {
        val type = object : TypeToken<List<Genre?>?>() {}.type
        return Gson().fromJson<List<Genre>>(str, type)
    }

    @TypeConverter
    fun toNetworksList(str: String?): List<Network>? {
        val type = object : TypeToken<List<Network?>?>() {}.type
        return Gson().fromJson<List<Network>>(str, type)
    }

}