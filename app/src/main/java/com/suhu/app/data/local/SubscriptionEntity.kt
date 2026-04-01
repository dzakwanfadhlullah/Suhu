package com.suhu.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriptions")
data class SubscriptionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val price: Double,
    val billingCycle: String,
    val nextBillingDate: Long,
    val isAutoRenew: Boolean
)
