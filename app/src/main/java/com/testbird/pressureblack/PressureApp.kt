package com.testbird.pressureblack

import android.app.Application
lateinit var appContext:Application

class PressureApp:Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
        registerActivityLifecycleCallbacks(LifecycleHelper())
    }
}