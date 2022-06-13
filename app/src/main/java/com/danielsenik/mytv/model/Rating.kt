package com.danielsenik.mytv.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rating(
    val average: Float
) : Parcelable
