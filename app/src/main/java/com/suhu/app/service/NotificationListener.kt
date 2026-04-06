package com.suhu.app.service

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class NotificationListener : NotificationListenerService() {

    companion object {
        private val recentTransactions = mutableMapOf<String, Long>()
        private const val DEBOUNCE_WINDOW_MS = 10_000L
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d("SuhuNotification", "Suhu Notification Listener Connected & Ready!")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        sbn ?: return

        try {
            val packageName = sbn.packageName

            // Hanya pedulikan paket dari aplikasi finance yang di-*support*
            if (!SupportedApps.BANKING_AND_EWALLET_APPS.contains(packageName)) {
                return
            }

            val extras = sbn.notification.extras
            val title = extras.getCharSequence(Notification.EXTRA_TITLE)?.toString() ?: ""
            val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString() ?: ""
            val bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString() ?: ""

            // Menggabungkan teks untuk diparsing
            val fullBody = if (bigText.isNotBlank()) "$title $bigText" else "$title $text"

            // Ekstraksi Nominal dan nama Merchant via Regex Parser dengan perlindungan ekstrem fallback
            val parsedAmount: Double?
            val parsedMerchant: String?
            try {
                parsedAmount = RegexParser.parseAmount(fullBody)
                parsedMerchant = RegexParser.parseMerchant(fullBody)
            } catch (e: Exception) {
                Log.w("SuhuNotification", ">> FAIL-SAFE: Format ekstrem/tidak standar mengunci RegexParser. Diabaikan.", e)
                return
            }

            Log.d("SuhuNotification", "==============================")
            Log.d("SuhuNotification", "🏦 Source   : $packageName")
            Log.d("SuhuNotification", "📌 Raw Text : $fullBody")
            Log.d("SuhuNotification", "💰 Nominal  : ${parsedAmount ?: "Tidak Ditemukan"}")
            Log.d("SuhuNotification", "🏢 Merchant : ${parsedMerchant ?: "Tidak Ditemukan"}")
            Log.d("SuhuNotification", "==============================")

            // Jika berhasil mengekstrak nominal dan merchant yang sah, selanjutnya data ini
            // akan disimpan ke database sebagai langganan (langkah Fase 6.3 / Integrasi Database).
            if (parsedAmount != null && parsedMerchant != null) {
                // EDGE CASE: Deduplikasi Transaksi (Notifikasi Ganda dalam 10 detik)
                val txKey = "${parsedMerchant}_${parsedAmount}"
                val now = System.currentTimeMillis()
                val lastSeen = recentTransactions[txKey] ?: 0L

                if (now - lastSeen < DEBOUNCE_WINDOW_MS) {
                    Log.d("SuhuNotification", ">> BLOCKED: Notifikasi ganda terdeteksi (Debounce 10s). Mengabaikan duplikat. <<")
                    return
                }
                recentTransactions[txKey] = now

                Log.d("SuhuNotification", ">> ACTION: Menganalisis Recurrence transaksi... <<")
                
                // Mendapatkan Repositori dari AppContainer
                val application = applicationContext as com.suhu.app.SuhuApplication
                val repository = application.container.subscriptionRepository
                
                // Masukkan ke mesin detektor
                RecurrenceDetector.analyzeAndProcessTransaction(
                    parsedAmount = parsedAmount,
                    parsedMerchant = parsedMerchant, 
                    repository = repository
                )
            } else {
                // FALLBACK: Notifikasi promo, transfer biasa, atau format tidak dikenali.
                Log.d("SuhuNotification", ">> FALLBACK: Format tidak dikenali atau bukan transaksi. Diabaikan. <<")
            }
        } catch (e: Exception) {
            Log.e("SuhuNotification", "Terjadi kesalahan fatal saat memparsing notifikasi: ${e.message}")
            e.printStackTrace()
        }
    }
}
