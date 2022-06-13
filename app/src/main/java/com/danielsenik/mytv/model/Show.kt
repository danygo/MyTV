package com.danielsenik.mytv.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "shows")
@Parcelize
data class Show(
    @PrimaryKey val id: Int,
    val url: String,
    val name: String,
    val genres: List<String>,
    val rating: Rating,
    val image: Image,
    val language: String,
    val summary: String
) : Parcelable
