package com.suhu.app.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.suhu.app.util.ServiceKeeper

/**
 * Menerima broadcast dari OS Android saat perangkat selesai melakukan proses booting.
 * Digunakan untuk:
 * 1. Me-reschedule Alarm/Worker.
 * 2. Memastikan NotificationListenerService terbangun dan binding kembali ke sistem.
 */
class BootReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("SuhuBootReceiver", "⚡ Perangkat selesai booting. Membangunkan Suhu Guard...")
            
            // 1. Reschedule Reminder Worker (WorkManager sebenernya persisten, tp aman buat dipanggil lagi)
            ServiceKeeper.scheduleDailyReminder(context.applicationContext)
            
            // 2. Trik untuk memaksa OS me-rebind Notification Listener Service
            ServiceKeeper.toggleNotificationListenerService(context.applicationContext)
        }
    }
}
