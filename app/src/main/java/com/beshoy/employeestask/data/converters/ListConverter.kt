package com.beshoy.employeestask.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {
    @TypeConverter
    fun fromList(value: MutableList<String>) = Gson().toJson(value)

    @TypeConverter
    fun toList(value: String): MutableList<String> {
        val type = object : TypeToken<MutableList<String>>() {}.type
        return Gson().fromJson(value, type)
    }
}