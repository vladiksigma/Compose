package com.example.compose.game

data class GameUiState(
    val attemptsLeft: Int = 10,
    val inputText: String = "",
    val hintMessage: String = "",
    val isGameOver: Boolean = false,
    val isWin: Boolean = false,
    val showPlayAgainButton: Boolean = false,

    val minNumber: Int = 1,
    val maxNumber: Int = 20
)