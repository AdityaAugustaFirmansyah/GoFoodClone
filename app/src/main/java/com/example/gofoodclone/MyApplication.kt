package com.example.gofoodclone

import android.app.Application
import com.example.session.TinyDB

class MyApplication : Application() {
    lateinit var tinyDB: com.example.session.TinyDB
    override fun onCreate() {
        super.onCreate()
        tinyDB = com.example.session.TinyDB(this)
    }
}