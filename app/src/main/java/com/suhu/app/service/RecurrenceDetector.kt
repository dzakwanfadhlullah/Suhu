package com.suhu.app.service

import com.suhu.app.data.local.SubscriptionEntity
import com.suhu.app.data.local.TransactionEntity
import com.suhu.app.data.repository.SubscriptionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Mesin Deteksi Langganan Berulang ("Secret Sauce").
 * Menganalisis transaksi individual untuk menyimpulkan apakah ia layak
 * diangkat ('promosi') menjadi Langganan tetap.
 */
object RecurrenceDetector {

    // Daftar merchant raksasa yang sudah 100% dipastikan model bisnisnya berlangganan.
    // Jika masuk radar, langsung di-bypass sebagai Subscription.
    private val GUARANTEED_SUBSCRIPTIONS = setOf(
        "NETFLIX", "SPOTIFY", "APPLE", "GOOGLE", "YOUTUBE", "DISNEY", "CANVA", "VIU", "VIDIO", "MICROSOFT"
    )

    private const val DAY_IN_MILLIS = 24L * 60L * 60L * 1000L

    fun analyzeAndProcessTransaction(
        parsedAmount: Double,
        parsedMerchant: String,
        repository: SubscriptionRepository
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val currentTime = System.currentTimeMillis()
            val newTrans = TransactionEntity(
                merchantName = parsedMerchant,
                amount = parsedAmount,
                timestamp = currentTime
            )

            // Simpan raw transaction terlebih dahulu
            repository.insertTransaction(newTrans)

            // 1. Rule Pertama: Bypass Dictionary Force (Guaranteed Subscription)
            if (GUARANTEED_SUBSCRIPTIONS.contains(parsedMerchant.uppercase())) {
                promoteToSubscription(parsedMerchant, parsedAmount, currentTime, repository)
                return@launch
            }

            // 2. Rule Kedua: Historical Match Analysis
            // Cari transaksi dengan nama merchant dan nominal persis di 20 - 40 hari yang lalu
            val startWindow = currentTime - (40 * DAY_IN_MILLIS)
            val endWindow = currentTime - (20 * DAY_IN_MILLIS)

            val previousMatch = repository.findMatchingTransactionInWindow(
                merchantName = parsedMerchant,
                amount = parsedAmount,
                startTime = startWindow,
                endTime = endWindow
            )

            if (previousMatch != null && !previousMatch.isProcessedAsSubscription) {
                // Ada histori pembayaran dengan merchant & nominal yang sama persis sekitar sebulan lalu!
                // INI ADALAH LANGGANAN! (Contoh: Uang Kas, Gym Lokal, dll)
                promoteToSubscription(parsedMerchant, parsedAmount, currentTime, repository)

                // Tandai transaksi lampau agar tidak ke-trigger dua kali
                repository.markTransactionAsProcessed(previousMatch.id)
            }
        }
    }

    private suspend fun promoteToSubscription(
        merchantName: String,
        amount: Double,
        timestamp: Long,
        repository: SubscriptionRepository
    ) {
        val nextBillingDateEstimate = timestamp + (30 * DAY_IN_MILLIS) // Asumsi bulanan

        val newSub = SubscriptionEntity(
            name = merchantName,
            price = amount,
            billingCycle = "Bulanan",
            nextBillingDate = nextBillingDateEstimate,
            isAutoRenew = true
        )
        repository.insertSubscription(newSub)
    }
}
