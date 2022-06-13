package com.danielsenik.mytv.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.danielsenik.mytv.data.converter.GenreTypeConverter
import com.danielsenik.mytv.data.converter.ImageTypeConverter
import com.danielsenik.mytv.data.converter.RatingTypeConverter
import com.danielsenik.mytv.data.dao.ShowDao
import com.danielsenik.mytv.model.Show

@Database(entities = [Show::class], version = 1)
@TypeConverters(GenreTypeConverter::class, RatingTypeConverter::class, ImageTypeConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun showDao(): ShowDao
}