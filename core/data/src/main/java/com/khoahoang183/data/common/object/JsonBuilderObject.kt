package com.khoahoang183.data.common.`object`

import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.Date

object JsonBuilderObject {

    val gsonBuilder: GsonBuilder
        get() = GsonBuilder()
            .setLenient()
            .serializeNulls()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

    val moshiNetworkBuilder: Moshi.Builder
        get() = Moshi.Builder()
            // Parse the DateTime in this format: [yyyy-MM-ddThh:mm:ss.ssZ]
            // e.g: [2019-10-12T07:20:50.52Z]
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .add(KotlinJsonAdapterFactory())

    val moshiUserBuilder: Moshi.Builder
        get() = Moshi.Builder()
            // Parse the DateTime in this format: [yyyy-MM-ddThh:mm:ss.ssZ]
            // e.g: [2019-10-12T07:20:50.52Z]
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .add(KotlinJsonAdapterFactory())
}