package com.bignerdranch.android.criminalintent

import android.app.Application


/*Application represents your whole app. Everything here will perist until your app is destroyed.
* Application isn't affected by configuration changes. It's good for one time configurations.
* Must register this in the android manifest
*  */
class CriminalIntentApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        //because crime repository holds a reference to application it can't be garbage collected
        // until application dies! We can now call the get function from anywhere.
        CrimeRepository.initialize(this)
    }
}