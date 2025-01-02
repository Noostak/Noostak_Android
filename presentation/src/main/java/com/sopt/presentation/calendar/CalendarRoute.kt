package com.sopt.presentation.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CalendarRoute(
    paddingValues: PaddingValues,
    navigateToInfoScreen: () -> Unit
) {
    CalendarScreen(
        paddingValues = paddingValues,
        onNavigateToInfoScreen = navigateToInfoScreen
    )
}

@Composable
fun CalendarScreen(
    paddingValues: PaddingValues = PaddingValues(),
    onNavigateToInfoScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { onNavigateToInfoScreen() }) {
            Text("InfoScreen ㄱㄱ")
        }
    }
}
