package com.bignerdranch.android.criminalintent.database

import androidx.room.TypeConverter
import java.util.*

//Helps room deal with the Date() object
class CrimeTypeConverters {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }
}