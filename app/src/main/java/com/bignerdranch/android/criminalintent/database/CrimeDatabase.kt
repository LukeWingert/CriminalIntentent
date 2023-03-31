package com.bignerdranch.android.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.bignerdranch.android.criminalintent.Crime
import java.util.*

//defining a database with the Crime entity
/*
* General steps:
*  1.make type converters,
*  2.define entites and add them to the db
*  3.define a dao which is just a bridge between kotlin and sql queries
* */
@Database(entities = [Crime::class], version = 1)
@TypeConverters(CrimeTypeConverters::class)
abstract class CrimeDatabase : RoomDatabase(){
    abstract fun crimeDao(): CrimeDao
}