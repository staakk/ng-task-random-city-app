package io.github.staakk.randomcity.data.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

class LocalDateTimeConverter {

    @TypeConverter
    fun fromEpochSeconds(value: Long?): LocalDateTime? {
        if (value == null) return null
        return LocalDateTime.ofEpochSecond(value, 0, ZoneOffset.UTC)
    }

    @TypeConverter
    fun localDateTimeToEpochSeconds(localDateTime: LocalDateTime?): Long? {
        if (localDateTime == null) return null
        return localDateTime.atOffset(ZoneOffset.UTC).toEpochSecond()
    }
}