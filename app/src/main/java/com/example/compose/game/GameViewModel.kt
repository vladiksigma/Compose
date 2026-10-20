package com.example.compose.game

import androidx.compose.material3.Text
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.data.GameSettings
import com.example.compose.data.SettingsRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.log2
import kotlin.math.round
import kotlin.random.Random

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var secretNumber: Int = 0

    private var currentSettings: GameSettings = GameSettings()

    init {
        viewModelScope.launch {
            SettingsRepository.settings.collect { settings ->
                currentSettings = settings
                startNewGame()
            }
        }
    }


        fun onInputChange(text: String) {

        val cleaned = text.filter { it.isDigit() }
        _uiState.update { it.copy(inputText = cleaned) }
    }
    fun onCheckNumberClick()         {
        val state = _uiState.value

        if(state.isGameOver){
            return
        }

        val guess: Int
        if (state.inputText != ""){
            guess = state.inputText.toInt()
        }
        else{
            return
        }

        if(guess == secretNumber){
            _uiState.update {
                it.copy(
                    inputText = "",
                    hintMessage = "Вы победили!",
                    isWin = true,
                    isGameOver = true,
                    showPlayAgainButton = true
                )
            }
            return
        }



        if(guess > secretNumber){
            _uiState.update {
                it.copy(
                    attemptsLeft = state.attemptsLeft - 1,
                    inputText = "",
                    hintMessage = "Число должно быть меньше",
                    isWin = false,
                    isGameOver = false,
                    showPlayAgainButton = false
                )
            }
        }

        if(guess < secretNumber){
            _uiState.update {
                it.copy(
                    attemptsLeft = state.attemptsLeft - 1,
                    inputText = "",
                    hintMessage = "Число должно быть больше",
                    isWin = false,
                    isGameOver = false,
                    showPlayAgainButton = false
                )
            }
        }

        if(state.attemptsLeft - 1 <= 0){
            _uiState.update {
                it.copy(
                    inputText = "",
                    hintMessage = "Вы проиграли(((",
                    isWin = false,
                    isGameOver = true,
                    showPlayAgainButton = true
                )
            }
        }
    }
    fun startNewGame(){
        secretNumber = Random.nextInt(currentSettings.minNumber, currentSettings.maxNumber + 1)
        _uiState.value = GameUiState(
            minNumber = currentSettings.minNumber,
            maxNumber = currentSettings.maxNumber,
            attemptsLeft = round( log2((currentSettings.maxNumber - currentSettings.minNumber).toDouble())).toInt() + 3
        )
    }
}