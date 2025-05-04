package com.arkan0099.assessment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkan0099.assessment1.ui.screen.MainScreen
//import com.arkan0099.assessment1.navigation.SetupNavGraph
import com.arkan0099.assessment1.ui.theme.AssessmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AssessmentTheme {
                MainScreen()
            }
        }
    }
}

