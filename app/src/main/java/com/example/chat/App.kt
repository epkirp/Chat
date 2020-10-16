package com.example.chat

import android.app.Application
import com.example.chat.di.AppComponent
import com.example.chat.di.DaggerAppComponent
import com.example.gateway.SharedPreferencesGateway

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        SharedPreferencesGateway.init(this)

        appComponent = DaggerAppComponent
            .builder()
            //.appModule(AppModule(this))
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}
