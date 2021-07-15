package com.example.weather_android_app.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    val title: String,
    @Json(name="latt_long") val coordinate: String
): Parcelable