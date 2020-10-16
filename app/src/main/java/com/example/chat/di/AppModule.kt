package com.example.chat.di

import com.example.chat.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideAppModule(): App {
        return app
    }
}