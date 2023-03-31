package com.bignerdranch.android.criminalintent.database

import androidx.room.Dao
import androidx.room.Query
import com.bignerdranch.android.criminalintent.Crime
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface CrimeDao {
    //You don't need to suspend if all you are doing is returning a handle to a flow
    @Query("SELECT * FROM crime")
    fun getCrimes(): Flow<List<Crime>>

    //The query just takes the params from the function and generates an implementation
    @Query("SELECT * FROM crime WHERE id=(:id)")
    suspend fun getCrime(id: UUID): Crime
}