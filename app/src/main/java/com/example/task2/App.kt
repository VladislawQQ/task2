package com.example.task2

import android.app.Application
import android.content.ContentResolver

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
    }

    companion object {
        private lateinit var app: App
        val contentResolverInstance: ContentResolver get() = app.contentResolver
    }
}