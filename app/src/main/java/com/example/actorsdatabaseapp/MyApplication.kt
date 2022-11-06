package com.example.actorsdatabaseapp

import android.app.Application
import com.example.actorsdatabaseapp.data.sqlite.AppSQLiteHelper

class MyApplication : Application() {

    lateinit var sqLiteHelper: AppSQLiteHelper

    override fun onCreate() {
        super.onCreate()
        sqLiteHelper = AppSQLiteHelper(applicationContext)
    }
}