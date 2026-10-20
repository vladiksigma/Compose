package com.example.compose

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.example.compose.navigation.AppNavHost
import com.example.compose.ui.theme.ComposeTheme




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                AppNavHost()
            }
        }
    }
}


