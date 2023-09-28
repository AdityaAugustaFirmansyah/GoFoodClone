package com.example.gofoodclone

import android.app.Application
import com.example.gofoodclone.framework.TinyDB

class MyApplication : Application() {
    lateinit var tinyDB: TinyDB
    override fun onCreate() {
        super.onCreate()
        tinyDB = TinyDB(this)
    }
}