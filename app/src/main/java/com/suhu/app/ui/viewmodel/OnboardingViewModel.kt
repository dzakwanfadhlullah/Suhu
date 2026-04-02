package com.suhu.app.ui.viewmodel

import android.content.ComponentName
import android.content.Context
import android.provider.Settings
import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class OnboardingState(
    val hasNotificationPermission: Boolean = false
)

class OnboardingViewModel : ViewModel() {

    private val _state = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    /**
     * Memeriksa apakah aplikasi ini sudah diberikan akses Notification Listener
     */
    fun checkPermission(context: Context) {
        val pkgName = context.packageName
        val flat = Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
        var hasPermission = false
        if (!TextUtils.isEmpty(flat)) {
            val names = flat.split(":")
            for (name in names) {
                val componentName = ComponentName.unflattenFromString(name)
                if (componentName != null && TextUtils.equals(pkgName, componentName.packageName)) {
                    hasPermission = true
                    break
                }
            }
        }
        
        _state.update { it.copy(hasNotificationPermission = hasPermission) }
    }

    // Karena tidak memerlukan Repository, Factory default bisa dipakai,
    // namun kita buat eksplisit agar seragam polanya.
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return OnboardingViewModel() as T
            }
        }
    }
}
