package com.bignerdranch.android.criminalintent

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

private const val TAG = "CrimeListViewModel"
class CrimeListViewModel : ViewModel(){
    private val crimeRepository = CrimeRepository.get()

    //StateFlows are similar to flows except they cache the last sent state to avoid db calls (config changes destroy fragments and call collect again)
    //We don't want consumers of our flow to be able to modify anything in the flow hence we protect
    //our internal mutable copy by returning a non-mutable stateflow version of it
    private val _crimes : MutableStateFlow<List<Crime>> = MutableStateFlow(emptyList())
    val crimes: StateFlow<List<Crime>>
        get() = _crimes.asStateFlow()
    init {
        viewModelScope.launch {
            //Whenever our flow has a new value we collect it and put into our MutableStateFlow
            crimeRepository.getCrimes().collect() {
                _crimes.value = it
            }
        }
    }
}