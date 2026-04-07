package com.suhu.app.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// ==========================================
// ENTITY: Tabel Langganan (subscriptions)
//
// INDEX STRATEGY (Fase 8.4 — Performance):
// - name          → Pencarian/filter berdasarkan nama layanan
// - nextBillingDate → Query tagihan mendatang (ORDER BY, WHERE range)
// - billingCycle   → Filter berdasarkan siklus penagihan
// ==========================================
@Entity(
    tableName = "subscriptions",
    indices = [
        Index(value = ["name"]),
        Index(value = ["nextBillingDate"]),
        Index(value = ["billingCycle"])
    ]
)
data class SubscriptionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "billingCycle")
    val billingCycle: String,

    @ColumnInfo(name = "nextBillingDate")
    val nextBillingDate: Long,

    @ColumnInfo(name = "isAutoRenew")
    val isAutoRenew: Boolean
)
