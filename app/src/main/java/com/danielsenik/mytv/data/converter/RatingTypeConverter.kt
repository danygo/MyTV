package com.danielsenik.mytv.data.converter

import androidx.room.TypeConverter
import com.danielsenik.mytv.model.Rating
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RatingTypeConverter {
    @TypeConverter
    fun fromString(rating: String): Rating {
        return Gson().fromJson(rating, object : TypeToken<Rating>() {}.type)
    }

    @TypeConverter
    fun fromObject(rating: Rating): String {
        return Gson().toJson(rating)
    }
}