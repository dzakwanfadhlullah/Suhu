package com.suhu.app.data.repository

import com.suhu.app.data.local.SubscriptionDao
import com.suhu.app.data.local.SubscriptionEntity
import kotlinx.coroutines.flow.Flow

interface SubscriptionRepository {
    fun getAllSubscriptions(): Flow<List<SubscriptionEntity>>
    suspend fun getSubscriptionById(id: Long): SubscriptionEntity?
    suspend fun insertSubscription(subscription: SubscriptionEntity)
    suspend fun deleteSubscription(id: Long)
}

class SubscriptionRepositoryImpl(
    private val dao: SubscriptionDao
) : SubscriptionRepository {

    override fun getAllSubscriptions(): Flow<List<SubscriptionEntity>> =
        dao.getAllSubscriptions()

    override suspend fun getSubscriptionById(id: Long): SubscriptionEntity? =
        dao.getSubscriptionById(id)

    override suspend fun insertSubscription(subscription: SubscriptionEntity) =
        dao.insertSubscription(subscription)

    override suspend fun deleteSubscription(id: Long) =
        dao.deleteSubscription(id)
}
