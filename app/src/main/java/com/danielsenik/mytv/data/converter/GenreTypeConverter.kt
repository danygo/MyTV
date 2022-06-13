package com.danielsenik.mytv.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreTypeConverter {
    @TypeConverter
    fun fromString(genres: String): List<String> {
        return Gson().fromJson(genres, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun fromObject(genres: List<String>): String {
        return Gson().toJson(genres)
    }
}