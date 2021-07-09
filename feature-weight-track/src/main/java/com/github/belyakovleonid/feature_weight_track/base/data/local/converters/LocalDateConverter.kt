package com.github.belyakovleonid.feature_weight_track.base.data.local.converters

import androidx.room.TypeConverter
import java.time.LocalDate

class LocalDateConverter {

    @TypeConverter
    fun save(date: LocalDate?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun restore(str: String?): LocalDate? {
        return if (str != null) {
            LocalDate.parse(str)
        } else {
            null
        }
    }
}