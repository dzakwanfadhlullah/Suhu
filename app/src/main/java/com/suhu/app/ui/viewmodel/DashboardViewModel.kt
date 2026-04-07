package com.suhu.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.compose.runtime.Immutable
import com.suhu.app.SuhuApplication
import com.suhu.app.data.local.SubscriptionEntity
import com.suhu.app.data.repository.SubscriptionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

// ==========================================
// STATE: Dashboard UI State
//
// @Immutable — Memberi tahu Compose compiler bahwa class ini
// tidak akan bermutasi setelah dibuat (semua field adalah val).
// Menghindari recomposition yang tidak perlu saat state sama.
// ==========================================
@Immutable
data class DashboardState(
    val subscriptions: List<SubscriptionEntity> = emptyList(),
    val totalExpense: Double = 0.0,
    val isLoading: Boolean = true
)

class DashboardViewModel(
    private val repository: SubscriptionRepository
) : ViewModel() {

    // Mengonversi data stream konstan dari Room secara otomatis menjadi UI State
    val dashboardState: StateFlow<DashboardState> = repository.getAllSubscriptions()
        .map { subs ->
            val total = subs.sumOf { it.price }
            DashboardState(
                subscriptions = subs,
                totalExpense = total,
                isLoading = false
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DashboardState(isLoading = true)
        )

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                // Mendapatkan instance app dari CreationExtras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                val container = (application as SuhuApplication).container
                
                return DashboardViewModel(container.subscriptionRepository) as T
            }
        }
    }
}
