package com.bignerdranch.android.criminalintent

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//Entity annotation the structure to a possible table in a db
@Entity
data class Crime(
    @PrimaryKey val id : UUID,
    val title : String,
    val date: Date,
    val isSolved : Boolean
)
