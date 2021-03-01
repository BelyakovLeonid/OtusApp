package com.example.otusapp

import android.app.Application
import com.example.otusapp.base.di.AppComponent
import com.example.otusapp.base.di.DaggerAppComponent

class OtusApp : Application() {

    val appComponent: AppComponent by lazy { DaggerAppComponent.create() }
}