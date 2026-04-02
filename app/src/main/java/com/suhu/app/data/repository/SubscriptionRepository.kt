package com.suhu.app.data.repository

import com.suhu.app.data.local.SubscriptionDao
import com.suhu.app.data.local.SubscriptionEntity
import com.suhu.app.data.local.TransactionDao
import com.suhu.app.data.local.TransactionEntity
import kotlinx.coroutines.flow.Flow

interface SubscriptionRepository {
    fun getAllSubscriptions(): Flow<List<SubscriptionEntity>>
    suspend fun getSubscriptionById(id: Long): SubscriptionEntity?
    suspend fun insertSubscription(subscription: SubscriptionEntity)
    suspend fun deleteSubscription(id: Long)
    
    suspend fun insertTransaction(transaction: TransactionEntity)
    suspend fun findMatchingTransactionInWindow(merchantName: String, amount: Double, startTime: Long, endTime: Long): TransactionEntity?
    suspend fun markTransactionAsProcessed(transactionId: Long)
}

class SubscriptionRepositoryImpl(
    private val subDao: SubscriptionDao,
    private val transDao: TransactionDao
) : SubscriptionRepository {

    override fun getAllSubscriptions(): Flow<List<SubscriptionEntity>> =
        subDao.getAllSubscriptions()

    override suspend fun getSubscriptionById(id: Long): SubscriptionEntity? =
        subDao.getSubscriptionById(id)

    override suspend fun insertSubscription(subscription: SubscriptionEntity) =
        subDao.insertSubscription(subscription)

    override suspend fun deleteSubscription(id: Long) =
        subDao.deleteSubscription(id)
        
    override suspend fun insertTransaction(transaction: TransactionEntity) =
        transDao.insertTransaction(transaction)
        
    override suspend fun findMatchingTransactionInWindow(
        merchantName: String, amount: Double, startTime: Long, endTime: Long
    ): TransactionEntity? = transDao.findMatchingTransactionInWindow(merchantName, amount, startTime, endTime)
    
    override suspend fun markTransactionAsProcessed(transactionId: Long) =
        transDao.markAsProcessed(transactionId)
}
