package com.android.framework.mvvm.data.database.typeconverters

import androidx.room.TypeConverter
import java.util.*

class Convertors {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }
}
