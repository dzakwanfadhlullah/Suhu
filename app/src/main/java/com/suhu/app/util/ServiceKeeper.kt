package com.suhu.app.util

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.suhu.app.service.NotificationListener
import com.suhu.app.worker.ReminderWorker
import java.util.concurrent.TimeUnit

object ServiceKeeper {

    /**
     * Toggles the NotificationListenerService component to force the Android System to rebind it.
     * This is the standard keep-alive strategy when OS kills background listener service.
     */
    fun toggleNotificationListenerService(context: Context) {
        try {
            val pm = context.packageManager
            val componentName = ComponentName(context, NotificationListener::class.java)

            // Disable component
            pm.setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )

            // Enable component
            pm.setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )

            Log.d("SuhuServiceKeeper", "🔁 NotificationListenerService toggled to force rebind.")
        } catch (e: Exception) {
            Log.e("SuhuServiceKeeper", "Failed to toggle service: ${e.message}")
        }
    }

    /**
     * Reschedules the Daily Reminder WorkManager.
     * Can be called after reboot (from BootReceiver).
     */
    fun scheduleDailyReminder(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "SuhuDailyReminder",
            ExistingPeriodicWorkPolicy.UPDATE,
            periodicWorkRequest
        )
        Log.d("SuhuServiceKeeper", "⏰ Daily reminder successfully scheduled.")
    }
}
