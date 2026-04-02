package com.suhu.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val merchantName: String,
    val amount: Double,
    val timestamp: Long,
    val isProcessedAsSubscription: Boolean = false
)
