package com.arkan0099.assessment1.ui.theme

import androidx.compose.runtime.Composable
import com.arkan0099.assessment1.ui.theme.green.GreenTheme
import com.arkan0099.assessment1.ui.theme.normal.AssessmentTheme

@Composable
fun ThemeController(
    isGreenTheme: Boolean,
    content: @Composable () -> Unit
) {
    if (isGreenTheme) {
        GreenTheme(content = content)
    } else {
        AssessmentTheme(content = content)
    }
}