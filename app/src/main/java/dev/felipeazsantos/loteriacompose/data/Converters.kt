package dev.felipeazsantos.loteriacompose.data

import androidx.room.TypeConverter
import java.util.Date

class Converters {

    @TypeConverter
    fun dateToTimestamp(date: Date) : Long {
         return date.time
    }

    @TypeConverter
    fun timeStampToDate(time: Long) : Date {
        return Date(time)
    }
}