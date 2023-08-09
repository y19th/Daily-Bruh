package com.example.dailybruh.activity

import android.app.Application
import com.example.dailybruh.dagger.DaggerMainComponent
import com.example.dailybruh.dagger.MainComponent

class MainApp : Application() {

    private var _appComponent: MainComponent? = null
    val appComponent: MainComponent get() = requireNotNull(_appComponent)

    override fun onCreate() {
        _appComponent = DaggerMainComponent.builder()
            .context(context = this)
            .build()
        super.onCreate()
    }
}