package id.fawwaz.wordle

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import id.fawwaz.wordle.screens.GameScreen
import id.fawwaz.wordle.theme.WordleTheme
import id.fawwaz.wordle.theme.backgroundColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    WordleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.backgroundColor
        ) {
            GameScreen()
        }
    }
}
