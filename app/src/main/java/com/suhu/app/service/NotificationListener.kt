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

        val fullBody = if (bigText.isNotBlank()) bigText else text

        Log.d("SuhuNotification", "==============================")
        Log.d("SuhuNotification", "🏦 Source : $packageName")
        Log.d("SuhuNotification", "📌 Title  : $title")
        Log.d("SuhuNotification", "💬 Body   : $fullBody")
        Log.d("SuhuNotification", "==============================")

        // TODO: Di fase 6.2, kita akan melempar fullBody ke RegexParser 
        // lalu menyimpannya ke Database menggunakan WorkManager/Coroutine
    }
}
