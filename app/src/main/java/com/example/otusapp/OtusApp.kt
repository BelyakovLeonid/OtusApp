package com.example.otusapp

import android.app.Application
import com.github.terrakok.cicerone.Cicerone

class OtusApp : Application() {

    companion object {
        val cicerone = Cicerone.create()
    }
}