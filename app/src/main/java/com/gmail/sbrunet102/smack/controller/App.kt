package com.gmail.sbrunet102.smack.controller

import android.app.Application
import com.gmail.sbrunet102.smack.utilities.SharedPrefs

class App:Application() {

    companion object{
        lateinit var prefs:SharedPrefs
    }

    override fun onCreate() {
        prefs = SharedPrefs(applicationContext)
        super.onCreate()
    }

}