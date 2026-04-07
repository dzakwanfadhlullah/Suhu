package com.suhu.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [SubscriptionEntity::class, TransactionEntity::class], 
    version = 3, 
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun subscriptionDao(): SubscriptionDao
    abstract fun transactionDao(): TransactionDao
}
