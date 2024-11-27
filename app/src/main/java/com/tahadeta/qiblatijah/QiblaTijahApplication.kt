package com.tahadeta.qiblatijah

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class QiblaTijahApplication: Application(){

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

    }
}