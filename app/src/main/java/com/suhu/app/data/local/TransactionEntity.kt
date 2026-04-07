package com.suhu.app.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// ==========================================
// ENTITY: Tabel Transaksi (transactions)
//
// INDEX STRATEGY (Fase 8.4 — Performance):
// - merchantName         → Filter berdasarkan nama merchant
// - timestamp            → Range query (BETWEEN) di RecurrenceDetector
// - merchantName + amount → Composite index untuk query
//                           findMatchingTransactionInWindow() yang memfilter
//                           kedua kolom secara bersamaan
// ==========================================
@Entity(
    tableName = "transactions",
    indices = [
        Index(value = ["merchantName"]),
        Index(value = ["timestamp"]),
        Index(value = ["merchantName", "amount"])
    ]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "merchantName")
    val merchantName: String,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long,

    @ColumnInfo(name = "isProcessedAsSubscription")
    val isProcessedAsSubscription: Boolean = false
)
