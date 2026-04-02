package com.suhu.app

import android.app.Application
import com.suhu.app.di.AppContainer
import com.suhu.app.di.DefaultAppContainer

class SuhuApplication : Application() {
    
    // Instance tunggal dari AppContainer yang akan digunakan oleh seluruh aplikasi
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}
