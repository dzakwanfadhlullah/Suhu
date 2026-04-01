package com.suhu.app.data.local

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class NotificationListener : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        // Logika ekstraksi regex akan ditaruh di sini untuk memproses notifikasi bank/e-wallet
    }
}
