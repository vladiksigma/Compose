package com.example.compose.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.game.GameScreen
import com.example.compose.game.GameViewModel

@Composable
fun SettingsRoute(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        state = state,
        onMinChange = viewModel::onMinChange,
        onMaxChange = viewModel::onMaxChange,
        onSaveClick = { viewModel.onSaveClick(onSaved = onBack) }
    )
}

@Composable
fun SettingsScreen(
    state: SettingsUiState,
    onMinChange: (String) -> Unit,
    onMaxChange: (String) -> Unit,
    onSaveClick: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))
        Text("Настройки", fontSize = 32.sp)
        Spacer(Modifier.height(32.dp))
        NumberField(
            label = "Минимальное число",
            value = state.minInput,
            onValueChange = onMinChange
        )
        Spacer(Modifier.height(16.dp))
        NumberField(
            label = "Максимальное число",
            value = state.maxInput,
            onValueChange = onMaxChange
        )
        Spacer(Modifier.height(40.dp))
        Button(onClick = onSaveClick, modifier = Modifier.fillMaxWidth()) {
            Text("Сохранить", fontSize = 20.sp)

        }
    }
}









@Composable
private fun NumberField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}