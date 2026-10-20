package com.example.compose.settings

import androidx.lifecycle.ViewModel
import com.example.compose.data.GameSettings
import com.example.compose.data.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        val current = SettingsRepository.settings.value
        _uiState.value = SettingsUiState(
            minInput = current.minNumber.toString(),
            maxInput = current.maxNumber.toString(),
        )
    }

    fun onMinChange(text: String){
        val cleaned = text.filter { it.isDigit() }
        _uiState.update { it.copy(minInput = cleaned) }
    }

    fun onMaxChange(text: String){
        val cleaned = text.filter { it.isDigit() }
        _uiState.update { it.copy(maxInput = cleaned) }
    }

    fun onSaveClick(onSaved: () -> Unit) {
        val state = _uiState.value

        val min = state.minInput.toIntOrNull()
        val max = state.maxInput.toIntOrNull()

        if (min != null && max != null && max > min){
            SettingsRepository.save(
                GameSettings(
                    minNumber = min,
                    maxNumber = max,
                )
            )
            onSaved()
        }
    }
}