package com.arkan0099.assessment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.arkan0099.assessment1.navigation.SetupNavGraph
import com.arkan0099.assessment1.ui.theme.ThemeController
import com.arkan0099.assessment1.util.SettingsDataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val dataStore = SettingsDataStore(LocalContext.current)
            val isGreenTheme = dataStore.themeFlow.collectAsState(initial = false).value

            ThemeController(isGreenTheme = isGreenTheme) {
                SetupNavGraph()
            }
        }
    }
}

