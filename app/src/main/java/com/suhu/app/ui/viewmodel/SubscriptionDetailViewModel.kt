package com.suhu.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.suhu.app.SuhuApplication
import com.suhu.app.data.local.SubscriptionEntity
import com.suhu.app.data.repository.SubscriptionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SubscriptionDetailState(
    val subscription: SubscriptionEntity? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isDeleted: Boolean = false
)

class SubscriptionDetailViewModel(
    private val repository: SubscriptionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SubscriptionDetailState())
    val state: StateFlow<SubscriptionDetailState> = _state.asStateFlow()

    fun loadSubscription(id: Long) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val data = repository.getSubscriptionById(id)
                if (data != null) {
                    _state.update { it.copy(subscription = data, isLoading = false) }
                } else {
                    _state.update { it.copy(error = "Data tidak ditemukan", isLoading = false) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun deleteSubscription() {
        val currentSub = _state.value.subscription ?: return
        viewModelScope.launch {
            repository.deleteSubscription(currentSub.id)
            _state.update { it.copy(isDeleted = true) }
        }
    }

    fun toggleAutoRenew() {
        val currentSub = _state.value.subscription ?: return
        viewModelScope.launch {
            val updatedSub = currentSub.copy(isAutoRenew = !currentSub.isAutoRenew)
            repository.insertSubscription(updatedSub)
            _state.update { it.copy(subscription = updatedSub) }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                val container = (application as SuhuApplication).container
                return SubscriptionDetailViewModel(container.subscriptionRepository) as T
            }
        }
    }
}
