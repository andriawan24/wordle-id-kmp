package id.fawwaz.wordle.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val ColorScheme.cardBackgroundNeutral: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFEBE9BB) else Color(0xFFEBE9BB)

val ColorScheme.backgroundColor: Color
    @Composable
    get() = Color(0xFF142A2F)
