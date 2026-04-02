package com.suhu.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    // Parameter time range untuk mengecek apakah bulan lalu dia pernah transaksi yang sama
    // Toleran dengan amount (misal pencarian dengan parameter nominal persis)
    @Query("""
        SELECT * FROM transactions 
        WHERE merchantName = :merchantName 
        AND amount = :amount
        AND timestamp BETWEEN :startTime AND :endTime
        LIMIT 1
    """)
    suspend fun findMatchingTransactionInWindow(
        merchantName: String,
        amount: Double,
        startTime: Long,
        endTime: Long
    ): TransactionEntity?

    @Query("UPDATE transactions SET isProcessedAsSubscription = 1 WHERE id = :transactionId")
    suspend fun markAsProcessed(transactionId: Long)
}
