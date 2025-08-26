package dev.felipeazsantos.loteriacompose

import android.app.Application
import dev.felipeazsantos.loteriacompose.data.AppDatabase

class App : Application() {
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getInstance(this)
    }
}