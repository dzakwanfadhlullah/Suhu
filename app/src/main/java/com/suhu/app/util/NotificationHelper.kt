package com.suhu.app.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.suhu.app.R

object NotificationHelper {

    private const val CHANNEL_ID = "suhu_upcoming_bills_channel"
    private const val CHANNEL_NAME = "Pengingat Tagihan"
    private const val CHANNEL_DESC = "Notifikasi untuk memberitahukan tagihan langganan yang akan segera jatuh tempo."

    /**
     * Daftarkan NotificationChannel. Wajib dilakukan di perangkat Android Oreo (8.0+) ke atas.
     */
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESC
            }
            // Daftarkan channel di sistem
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Menembakkan notifikasi peringatan berbunyi ke layar ponsel.
     */
    fun showUpcomingBillNotification(
        context: Context,
        notificationId: Int,
        merchantName: String,
        amountText: String,
        daysLeft: Int
    ) {
        // Buat channel terlebih dahulu (aman dipanggil berkali-kali)
        createNotificationChannel(context)

        val title = if (daysLeft <= 1) {
            "🚨 BESOK: Tagihan $merchantName"
        } else {
            "⚠️ H-$daysLeft: Tagihan $merchantName"
        }

        val text = "Langganan sebesar $amountText akan ditagih. Pastikan saldo Anda cukup!"

        // Gunakan ikon default Android jika belum ada icon aplikasinya.
        // TODO: Kita harus memastikan menggunakan R.mipmap.ic_launcher di realita, tapi notification icon wajib pakai alpha silhouette icon (transparent).
        // Untuk sekarang kita pakai icon bawaan android fallback.
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            
        notificationManager.notify(notificationId, builder.build())
    }
}
