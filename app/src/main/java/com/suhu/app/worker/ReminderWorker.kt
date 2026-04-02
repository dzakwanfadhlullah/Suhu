package com.suhu.app.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.suhu.app.SuhuApplication
import kotlinx.coroutines.flow.firstOrNull

/**
 * Pekerja (Worker) latar belakang yang dijadwalkan oleh Android WorkManager.
 * Bertugas mencari tagihan langganan mana yang sudah mendekati jatuh tempo (H-3).
 */
class ReminderWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("SuhuReminder", "==> ReminderWorker Mulai Berpatroli. Menganalisis Database... <==")
        return try {
            // Kita mengambil instance repositori dari App Container (DI Manual kita)
            val application = applicationContext as SuhuApplication
            val repository = application.container.subscriptionRepository

            // Mengambil secara statis list langganan terbaru
            val subscriptions = repository.getAllSubscriptions().firstOrNull() ?: emptyList()

            val currentTime = System.currentTimeMillis()
            val threeDaysInMillis = 3L * 24L * 60L * 60L * 1000L

            var foundUpcoming = false

            for (sub in subscriptions) {
                // Periksa apakah ini akan ditagih dalan kurang dari sama dengan H-3
                val timeDifference = sub.nextBillingDate - currentTime
                
                // Terdeteksi! Jatuh tempo berada dalam rentang H-3
                if (timeDifference in 0..threeDaysInMillis) {
                    val daysLeft = (timeDifference / (24L * 60L * 60L * 1000L)).toInt()
                    Log.d("SuhuReminder", "🚨 AWAS! Langganan [${sub.name}] akan jatuh tempo dalam $daysLeft hari! (Nominal: Rp ${sub.price})")
                    foundUpcoming = true
                    
                    // Format harga untuk UI
                    val formattedPrice = "Rp %,.0f".format(sub.price).replace(',', '.')
                    
                    // Memanggil Notification Helper untuk menembakkan Pop-up
                    com.suhu.app.util.NotificationHelper.showUpcomingBillNotification(
                        context = applicationContext,
                        notificationId = sub.id.toInt(), // Cukup gunakan ID database langganan sebagai ID notif
                        merchantName = sub.name,
                        amountText = formattedPrice,
                        daysLeft = daysLeft
                    )
                }
            }

            if (!foundUpcoming) {
                Log.d("SuhuReminder", "✅ Status Aman. Tidak ada tagihan jatuh tempo dalam 3 hari ke depan.")
            }

            Log.d("SuhuReminder", "==> ReminderWorker Selesai Berpatroli. <==")
            Result.success()
        } catch (e: Exception) {
            Log.e("SuhuReminder", "Terjadi kesalahan saat memeriksa tagihan: ${e.message}")
            Result.retry() // Coba lagi jika error database
        }
    }
}
