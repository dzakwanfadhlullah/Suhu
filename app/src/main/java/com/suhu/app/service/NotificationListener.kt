package com.suhu.app.service

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class NotificationListener : NotificationListenerService() {

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d("SuhuNotification", "Suhu Notification Listener Connected & Ready!")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        sbn ?: return

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

        // Ekstraksi Nominal dan nama Merchant via Regex Parser
        val parsedAmount = RegexParser.parseAmount(fullBody)
        val parsedMerchant = RegexParser.parseMerchant(fullBody)

        Log.d("SuhuNotification", "==============================")
        Log.d("SuhuNotification", "🏦 Source   : $packageName")
        Log.d("SuhuNotification", "📌 Raw Text : $fullBody")
        Log.d("SuhuNotification", "💰 Nominal  : ${parsedAmount ?: "Tidak Ditemukan"}")
        Log.d("SuhuNotification", "🏢 Merchant : ${parsedMerchant ?: "Tidak Ditemukan"}")
        Log.d("SuhuNotification", "==============================")

        // Jika berhasil mengekstrak nominal dan merchant yang sah, selanjutnya data ini
        // akan disimpan ke database sebagai langganan (langkah Fase 6.3 / Integrasi Database).
        if (parsedAmount != null && parsedMerchant != null) {
            Log.d("SuhuNotification", ">> MATCH DITEMUKAN! Siap dikirim ke Database. <<")
        }
    }
}
