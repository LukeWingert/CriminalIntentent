package com.bignerdranch.android.criminalintent

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

private const val TAG = "CrimeListViewModel"
class CrimeListViewModel : ViewModel(){
    val crimes =  mutableListOf<Crime>()

    init {
        Log.d(TAG, "init starting")
        viewModelScope.launch {
            Log.d(TAG, "coroutine starting")
            crimes += loadCrimes()
            Log.d(TAG, "Loading crimes finished")
        }
    }

    //a suspend function is a function that is allowed to be paused so the thread can go do something else
    //while blocked. They can only be ran inside of coroutine scopes
    suspend fun loadCrimes() : List<Crime> {
        val result = mutableListOf<Crime>()
        delay(5000)
        for (i in 0 until 100) {
            val crime = Crime(
                id = UUID.randomUUID(),
                title = "Crime #$i",
                date = Date(),
                isSolved = i % 2 == 0
            )
            //This syntax ❤❤❤
            result += crime
        }
        return result
    }
}