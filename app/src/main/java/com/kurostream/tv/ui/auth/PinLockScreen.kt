package com.kurostream.tv.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Text

@Composable
fun PinLockScreen(onPinComplete: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Enter PIN", color = Color.White, modifier = Modifier.padding(bottom = 32.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            for (i in 1..4) {
                PinDigit(digit = i) { 
                    // TODO: PHASE_3 Collect digits and verify
                }
            }
        }
    }
}

@Composable
fun PinDigit(digit: Int, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Box(
        modifier = Modifier
            .focusable(interactionSource = interactionSource)
            .background(if (isFocused) Color.White else Color.DarkGray)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = digit.toString(), color = if (isFocused) Color.Black else Color.White)
    }
}
