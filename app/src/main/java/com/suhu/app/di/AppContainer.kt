package com.suhu.app.di

import android.content.Context
import androidx.room.Room
import com.suhu.app.data.local.AppDatabase
import com.suhu.app.data.repository.SubscriptionRepository
import com.suhu.app.data.repository.SubscriptionRepositoryImpl

/**
 * Kontainer Dependency Injection (DI) Manual.
 * Menghindari penggunaan library eksternal (Hilt/Koin) untuk menjaga
 * aplikasi tetap ringan dan sesuai pedoman Vibe-Coding.
 */
interface AppContainer {
    val subscriptionRepository: SubscriptionRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    
    // Lazy initialization agar instance database hanya dibuat sekali
    private val database: AppDatabase by lazy {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "suhu_database"
        ).build()
    }

    override val subscriptionRepository: SubscriptionRepository by lazy {
        SubscriptionRepositoryImpl(database.subscriptionDao())
    }
}
