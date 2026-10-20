package com.example.compose.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow



data class GameSettings(
    val minNumber: Int = 1,
    val maxNumber: Int = 20,
)



object SettingsRepository {

    private val _settings = MutableStateFlow(GameSettings())


    val settings: StateFlow<GameSettings> = _settings.asStateFlow()


    fun save(newSettings: GameSettings) {
        _settings.value = newSettings
    }
}