package com.suhu.app

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.suhu.app.di.AppContainer
import com.suhu.app.di.DefaultAppContainer
import com.suhu.app.worker.ReminderWorker
import java.util.concurrent.TimeUnit

class SuhuApplication : Application() {
    
    // Instance tunggal dari AppContainer yang akan digunakan oleh seluruh aplikasi
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
        
        setupWorkManager()
    }

    private fun setupWorkManager() {
        // Hanya berjalan jika baterai tidak sedang sekarat agar hemat energi
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        // Dijadwalkan berjalan berulang setiap 1 hari (24 Jam)
        val periodicWorkRequest = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        // Masukkan (enqueue) ke sistem operasi dengan kebijakan REPLACE jika sudah ada pekerjaan sejenis
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "SuhuDailyReminder",
            ExistingPeriodicWorkPolicy.UPDATE,
            periodicWorkRequest
        )
    }
}
