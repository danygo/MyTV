package com.danielsenik.mytv.data.converter

import androidx.room.TypeConverter
import com.danielsenik.mytv.model.Image
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ImageTypeConverter {
    @TypeConverter
    fun fromString(image: String): Image {
        return Gson().fromJson(image, object : TypeToken<Image>() {}.type)
    }

    @TypeConverter
    fun fromObject(image: Image): String {
        return Gson().toJson(image)
    }
}