package com.bignerdranch.android.criminalintent

import android.content.Context
import androidx.room.Room
import com.bignerdranch.android.criminalintent.database.CrimeDatabase
import kotlinx.coroutines.flow.Flow
import java.util.UUID

private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) {
    private val database : CrimeDatabase = Room
        .databaseBuilder(
            context.applicationContext,  //We give it app context cuz it needs fs
            CrimeDatabase::class.java,   //The abstract class we created earlier
            DATABASE_NAME                //What we want to name it
        )
        .createFromAsset(DATABASE_NAME)
        .build()

    // using equals in a function definition is like using a one-liner func def
    fun getCrimes(): Flow<List<Crime>> = database.crimeDao().getCrimes()

    suspend fun getCrime(id:UUID): Crime = database.crimeDao().getCrime(id)

    companion object {
        // "CrimeRepository?" Just says that it's allowed to be null
        private var INSTANCE: CrimeRepository? = null


        //Only calls init if instance is null
        fun initialize(context: Context) {
            if(INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initalized")
        }
    }
}