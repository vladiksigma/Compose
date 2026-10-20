package com.example.compose.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameRoute(onOpenSettings: () -> Unit, viewModel: GameViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    GameScreen(
        state = state,
        onInputChange = viewModel::onInputChange,
        onCheckClick = viewModel::onCheckNumberClick,
        onPlayAgainClick = viewModel::startNewGame,
        onOpenSettings = onOpenSettings
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    state: GameUiState,
    onInputChange: (String) -> Unit,
    onCheckClick: () -> Unit,
    onPlayAgainClick: () -> Unit,
    onOpenSettings: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Угадай число", fontSize = 22.sp) },
                actions = {
                    IconButton(onClick = onOpenSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Настройки"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Spacer(Modifier.height(40.dp))

        Text("Попыток: ${state.attemptsLeft}")
        Spacer(Modifier.height(40.dp))
        TextField(value = state.inputText, onValueChange = onInputChange, placeholder = {Text("от ${state.minNumber} до ${state.maxNumber}")})
        Spacer(Modifier.height(40.dp))
        Text(state.hintMessage)
        Spacer(Modifier.height(40.dp))
        Button(onClick = onCheckClick) { Text("Проверить") }
        Spacer(Modifier.height(40.dp))
        if (state.isGameOver) {
            if (state.isWin) {
                Image(
                    contentDescription = "", painter = painterResource(R.drawable.win),
                    modifier = Modifier.size(250.dp)
                )
            } else {
                Image(
                    contentDescription = "", painter = painterResource(R.drawable.lose),
                    modifier = Modifier.size(250.dp)
                )

            }
        }
        Spacer(Modifier.height(40.dp))
        if (state.showPlayAgainButton) Button(onClick = onPlayAgainClick) { Text("Играть снова") }
        }
    }
}


