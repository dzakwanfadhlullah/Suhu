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

data class ManualEntryState(
    val name: String = "",
    val priceText: String = "",
    val category: String = "Lainnya",
    val billingCycle: String = "Bulanan",
    val nextBillingDate: Long = System.currentTimeMillis(),
    val isAutoRenew: Boolean = true,
    
    val nameError: String? = null,
    val priceError: String? = null,
    val isSaved: Boolean = false
)

class ManualEntryViewModel(
    private val repository: SubscriptionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ManualEntryState())
    val state: StateFlow<ManualEntryState> = _state.asStateFlow()

    fun updateName(name: String) {
        _state.update { it.copy(name = name, nameError = null) }
    }

    fun updatePrice(priceInput: String) {
        _state.update { it.copy(priceText = priceInput, priceError = null) }
    }

    fun updateCategory(category: String) {
        _state.update { it.copy(category = category) }
    }

    fun updateBillingCycle(cycle: String) {
        _state.update { it.copy(billingCycle = cycle) }
    }

    fun updateDate(date: Long) {
        _state.update { it.copy(nextBillingDate = date) }
    }

    fun updateAutoRenew(isAutoRenew: Boolean) {
        _state.update { it.copy(isAutoRenew = isAutoRenew) }
    }

    fun saveSubscription() {
        val currentState = _state.value
        var hasError = false
        
        // Basic Validation
        if (currentState.name.isBlank()) {
            _state.update { it.copy(nameError = "Nama langganan tidak boleh kosong") }
            hasError = true
        }
        
        val price = currentState.priceText.replace("[^\\d]".toRegex(), "").toDoubleOrNull()
        if (price == null || price <= 0) {
            _state.update { it.copy(priceError = "Harga langganan tidak valid") }
            hasError = true
        }

        if (hasError) return

        // Proses simpan
        viewModelScope.launch {
            repository.insertSubscription(
                SubscriptionEntity(
                    name = currentState.name,
                    price = price!!, // guaranteed non-null from above check
                    billingCycle = currentState.billingCycle,
                    nextBillingDate = currentState.nextBillingDate,
                    isAutoRenew = currentState.isAutoRenew
                )
            )
            
            // Set penanda berhasil menyimpan agar UI bisa menutup layar ini
            _state.update { it.copy(isSaved = true) }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                val container = (application as SuhuApplication).container
                return ManualEntryViewModel(container.subscriptionRepository) as T
            }
        }
    }
}
